package com.jithin.ecommerce.security;

import com.jithin.ecommerce.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.jithin.ecommerce.utils.constants.SECRET_KEY;
import static com.jithin.ecommerce.utils.constants.TOKEN_EXPIRATION_TIME;

@Component
public class JwtTokenProvider {

    public String generatingToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());

        String userId = Long.toString(user.getId());
        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("invalid signature");
        } catch (MalformedJwtException ex) {
            System.out.println("invalid jwt token");
        } catch (ExpiredJwtException ex) {
            System.out.println("token expired");
        } catch (UnsupportedJwtException ex) {
            System.out.println("un supported jwt");
        } catch (IllegalArgumentException ex) {
            System.out.println("illegal argument");
        }

        return false;
    }

    public Long getUserIdFromJwt(String token) {

        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }

}
