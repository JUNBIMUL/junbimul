package com.junbimul.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    public String createAccessToken(String loginId, String key, long expireTimeMs) {
        // Claim = Jwt Token에 들어갈 정보
        // Claim에 loginId를 넣어 줌으로서 나중에 loginId를 꺼낼 수 있다.
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();


    }

    public String createRefreshToken(String loginId, String key, long expireTimeMs) {
        // Claim = Jwt Token에 들어갈 정보
        // Claim에 loginId를 넣어 줌으로서 나중에 loginId를 꺼낼 수 있다.
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();


    }

    // Claims에서 loginId 꺼내기
    public String getLoginId(String token, String secretKey) {
        return extractClaims(token, secretKey).get("loginId").toString();
    }

    public boolean isExpired(String token, String secretKey) {
        Date expirationDate = extractClaims(token, secretKey).getExpiration();
        // Token 만료 날짜 지금보다 이전인지 체크
        return expirationDate.before(new Date());
    }

    private Claims  extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody();
    }

}
