package com.junbimul.config;

import com.junbimul.domain.User;
import com.junbimul.error.exception.UserApiException;
import com.junbimul.error.model.UserErrorCode;
import com.junbimul.repository.UserRepository;
import com.junbimul.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    private final String SECRET_KEY = "abcdefghihklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 60; // 5min
    private final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 60;  // 3min

    private final UserRepository userRepository;


    public final String generateAccessToken(String subject) {
        return generateToken(subject, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(String subject) {
        return generateToken(subject, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String generateToken(String loginId, long expirationTime) {
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }


    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getJwtTokenLoginId(String token) {
        return getClaims(token)
                .get("loginId", String.class);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token) {
        String loginId = getJwtTokenLoginId(token);
        System.out.println("loginId = " + loginId);
        List<User> findUserList = userRepository.findByLoginId(loginId);
        if (findUserList.size() == 0) {
            throw new UserApiException(UserErrorCode.USER_USERID_NOT_FOUND);
        }
        return isTokenExpired(token);
    }
}
