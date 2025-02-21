package ru.fomin.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Autowired

    public JwtTokenProvider(@Qualifier("UserDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Value("${jwt.expiration}")
    private Long validityInMilliseconds;
    @Value("${jwt.header}")
    private String authorizationHeaderName;

    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(String username, String role) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("role", role)
                .build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return claimsJws.getPayload()
                    .getExpiration()
                    .after(new Date());
        }catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthException("Invalid JWT", HttpStatus.UNAUTHORIZED);
        }
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest httpServletRequest){
        return httpServletRequest.getHeader(authorizationHeaderName);
    }


}
