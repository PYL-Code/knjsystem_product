package edu.du.knjsystem_product.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTestGenerator {
    public static void main(String[] args) {
        String secretKey = "mySecretKey"; // JwtTokenProvider와 동일해야 함

        String token = Jwts.builder()
                .setSubject("testuser") // 사용자 ID
                .claim("seqNoA001", "1") // 로그인 사용자의 고유번호
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        System.out.println("📦 테스트용 JWT 토큰:\nBearer " + token);
    }
}
