package com.jandro.market.web.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String KEY = "bA<QO/S!$H4#0$19{mm(=_RMUP640/~n(nGn+RkZYJn'h`[T0R,lMF$lHi/l}B*";

    public String obtainPairToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }

    public boolean verifyToken(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(extractUsername(token)) &&
                !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
