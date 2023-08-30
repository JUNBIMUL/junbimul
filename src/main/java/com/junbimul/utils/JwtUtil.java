package com.junbimul.utils;

import com.junbimul.common.JwtProperties;
import com.junbimul.domain.User;
import com.junbimul.error.exception.UserApiException;
import com.junbimul.error.model.UserErrorCode;
import com.junbimul.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    public final String generateAccessToken(String subject) {
        return generateToken(subject, jwtProperties.getAccessTokenExpirationTime());
    }

    public String generateRefreshToken(String subject) {
        return generateToken(subject, jwtProperties.getRefreshTokenExpirationTime());
    }

    private String generateToken(String loginId, long expirationTime) {
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey().getBytes())
                .compact();
    }


    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getJwtTokenLoginId(String token) {
        return getClaims(token)
                .get("loginId", String.class);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token) {
        String loginId = getJwtTokenLoginId(token);
        List<User> findUserList = userRepository.findByLoginId(loginId);
        if (findUserList.size() == 0) {
            throw new UserApiException(UserErrorCode.USER_USERID_NOT_FOUND);
        }
        return !isTokenExpired(token);
    }
}
