package esun.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import esun.user.User;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secretKey}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public Claims decryptToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(secret.getBytes())
            .build();
        return parser.parseClaimsJws(token).getBody();
    }
}

