package edu.du.knjsystem_product.jwt;

import edu.du.knjsystem_product.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenProvider {

    private final String secretKeyString = "mySuperSecureSecretKey1234567890!@#$";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace(); // 에러 원인 로그 확인
            return false;
        }
    }

    public CustomUserDetails getUserFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long seqNoA001 = Long.valueOf(claims.get("seqNoA001").toString());
        String username = claims.getSubject();

        return new CustomUserDetails(seqNoA001, username, "N/A");
    }
}
