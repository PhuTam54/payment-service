package com.example.paymentService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenUtil {

    private static final String jwt_secret = "quangthai3110quangthai3110quangthai3110";  // Tạo khóa ký an toàn
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwt_secret.getBytes());
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
    }
}
