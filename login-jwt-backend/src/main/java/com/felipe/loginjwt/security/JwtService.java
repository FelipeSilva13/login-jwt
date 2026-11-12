package com.felipe.loginjwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
@Data
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSecretKey() {
        byte[] encodedKey = Base64.getEncoder().encode(secret.getBytes());

        return Keys.hmacShaKeyFor(encodedKey);
    }

    public String gerarToken(UserDetails userDetails){

        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(agora)
                .expiration(dataExpiracao)
                .signWith(getSecretKey())
                .compact();

    }

    public String extrairUsername(String token){
        return extrairClaim(token, Claims::getSubject);
    }

    private <T> T extrairClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claimsResolver.apply(claims);

    }

    public boolean validaToken(String token, UserDetails userDetails){

        String username = extrairUsername(token);

        return username.equals(userDetails.getUsername());
    }


    public long getExpirationInSeconds() {
        return expiration / 1000;
    }


}
