package com.junbimul.config.security;

import com.junbimul.domain.User;
import com.junbimul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Header에 authoriztion 비어있으면 JWT 전송 X -> 로그인 X
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // Bearer 로 시작해야 맞는 토큰
        if (!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Bearer 뒷 값
        String token = authorizationHeader.split(" ")[1];

        // if 만료 -> X,
        if (jwtTokenProvider.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        String loginId = jwtTokenProvider.getLoginId(token, secretKey);
        User loginUser = userService.getUserByLoginId(loginId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getLoginId(), null, List.of(new SimpleGrantedAuthority("aa"))
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
