package com.lcwd.electronic.store.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public class JwtHelper {

    private final String secretKey = "dhiwmaimandymceu5d6s7s9s2ss7ss11sd7c2x5s8ss1s78s9w5rfgr8i4m1n1b2c8v8";
    private final long expireDate = 1 * 60 * 60 * 1000;

    public String getUsernameFromToken(String token) {
        return this.getAllClaims(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return this.getAllClaims(token).getExpiration();
    }

    public Claims getAllClaims(String token) {
        JwtParser parser = Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build();
        Claims payload = parser
                .parseSignedClaims(token)
                .getPayload();
        return payload;
    }

    public Boolean isTokenExpired(String token) {
        Date expireDate = this.getExpirationDateFromToken(token);
        return expireDate.before(new Date());
    }

    public String generateToken(String userName) {
        String token = Jwts.builder()
                .subject(userName)
                .expiration(new Date(System.currentTimeMillis() + expireDate))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSecretKey())
                .compact();
        return token;
    }

    public SecretKey getSecretKey() {
        byte[] bytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(bytes); // signature algo which required byte array.
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = userDetails.getUsername();
        return username.equals(this.getUsernameFromToken(token)) && !this.isTokenExpired(token);
    }


}
