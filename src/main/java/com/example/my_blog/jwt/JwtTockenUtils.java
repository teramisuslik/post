package com.example.my_blog.jwt;

import com.example.my_blog.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import static java.time.LocalTime.now;

@Component
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtTockenUtils {

    private String secret;
    private Integer lifetime;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }

    public String generateTocken(UserDetails userDetails){
        HashMap<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User user){
            claims.put("role", user.getRole().name());
        }
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + lifetime * 60000))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSigningKey(),  SignatureAlgorithm.HS256)
                .compact();

    }

    public Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Claims getClaimsAllFromTocken(String tocken){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(tocken).getBody();
    }

    public <T> T getClaimFromTocken(String tocken, Function<Claims, T> claimsResolver){
        Claims claims = getClaimsAllFromTocken(tocken);
        return claimsResolver.apply(claims);
    }
    public String getUsernameFromTocken(String tocken){
        return getClaimFromTocken(tocken, Claims::getSubject);
    }

    public boolean validateTocken(String tocken, UserDetails userDetails){
        String username = getUsernameFromTocken(tocken);
        return (username.equals(userDetails.getUsername()) && !isTockenExpired(tocken));
    }

    public boolean isTockenExpired(String tocken){
        Date expiration = getClaimFromTocken(tocken, Claims::getExpiration);
        return expiration.before(new Date());
    }
}
