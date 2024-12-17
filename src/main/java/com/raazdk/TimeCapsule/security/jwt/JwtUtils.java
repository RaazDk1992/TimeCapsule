package com.raazdk.TimeCapsule.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.app.jwtExpirationMs}")
    private String jwtExpiration;
    Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String extractJwt(HttpServletRequest request){

        String authHeader = request.getHeader("Authorization");

        if(authHeader !=null && authHeader.startsWith("Bearer")){
            return  authHeader.substring(7);
        }

        return null;
    }

    public  boolean validateJwt(String token){
        try{

            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);
            return  true;

        }catch (Exception e){
            return false;
        }
    }

    public String generateJwtFromUsername(UserDetails details){

        String username = details.getUsername();
        Date issuedDate = new Date();
        Date expiringDate = Date.from(issuedDate.toInstant()
                .plusMillis(Long.parseLong(jwtExpiration)));
        return Jwts.builder().subject(username)
                .issuedAt(issuedDate)
                .expiration(expiringDate)
                .signWith(key())
                .compact();

    }

    public String getUsernameFromToken(String token){
        String username = Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(token).getPayload().getSubject();

        return  username;
    }


    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


}
