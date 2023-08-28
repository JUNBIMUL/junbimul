package com.junbimul.config;

import com.junbimul.error.exception.InterceptorException;
import com.junbimul.error.model.InteceptorErrorCode;
import com.junbimul.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.split(" ")[1];
            try {
                if (jwtUtil.validateToken(jwt)) {
                    String loginId = jwtUtil.getJwtTokenLoginId(jwt);
                    request.setAttribute("loginId", loginId);
                    return true;
                }
                throw new InterceptorException(InteceptorErrorCode.UNAUTHORIZED);
            } catch (MalformedJwtException e) { // 위조 시도
                log.error("Malformed Jwt Token: {}", e.getMessage());
                throw new InterceptorException(InteceptorErrorCode.MALFORMED);

            } catch (ExpiredJwtException e) { // 만료된 토큰
                log.error("Expired Jwt Token: {}", e.getMessage());
                throw new InterceptorException(InteceptorErrorCode.EXPIRED);

            } catch (Exception e) {
                throw new InterceptorException(InteceptorErrorCode.EXPIRED);
            }

        }

        // TOKEN UNVALID
        throw new InterceptorException(InteceptorErrorCode.UNAUTHORIZED);
    }
}
