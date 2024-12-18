package com.example.springboot3demo.security.jwt;

import java.util.HashMap;
import java.util.Map;

public class JwtClaims {
    private String subject;
    private Map<String, Object> claims;

    public JwtClaims(String subject) {
        this.subject = subject;
        this.claims = new HashMap<>();
    }

    public String getSubject() {
        return subject;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }

    public void addClaim(String key, Object value) {
        this.claims.put(key, value);
    }
}
