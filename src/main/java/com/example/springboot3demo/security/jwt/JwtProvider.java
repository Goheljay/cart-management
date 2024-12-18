package com.example.springboot3demo.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider {

    public static  final  long JWT_TOCKEN_VALIDITY = 5*60*60;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    /**
     * Generate a JWT token
     *
     * @param email  Claims to include in the token
     * @return Generated JWT token
     */
    // Generate token with given user name
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email",email);
        return createToken(claims, email);
    }

    // Create a JWT token with specified claims and subject (user name)
    private String createToken(Map<String, Object> claims, String subject) {
        String compact = Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_TOCKEN_VALIDITY)).signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        return compact;
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        Claims body = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getBody();
        return body;
    }



//    /**
//     * Validate and parse a JWT token
//     *
//     * @param token JWT token to verify
//     * @return Parsed claims if valid
//     * @throws JwtException If the token is invalid or expired
//     */
//    public Boolean validateToken(String token) {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token);
//        } catch (JwtException ex) {
//            throw new IllegalArgumentException("Invalid or expired token", ex);
//        }
//    }
//
//    /**
//     * Decode a JWT token without verifying
//     *
//     * @param token JWT token to decode
//     * @return Decoded claims
//     */
//    public Claims decodeToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJwt(token)
//                .getBody();
//    }
}
