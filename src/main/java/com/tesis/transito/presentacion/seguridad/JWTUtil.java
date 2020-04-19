package com.tesis.transito.presentacion.seguridad;

import com.tesis.transito.presentacion.modelos.VistaUsuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ard333
 */
@Component
public class JWTUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    private String secret;
    private String expirationTime;

    public JWTUtil(@Value("${springbootwebfluxjjwt.jjwt.secret}") String secret,
                   @Value("${springbootwebfluxjjwt.jjwt.expiration}") String expirationTime) {
        this.secret = secret;
        this.expirationTime = expirationTime;
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes())).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(VistaUsuario user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("numeroDeTelefono", user.getNumeroDeTelefono());
        claims.put("correoElectronico", user.getCorreoElectronico());
        claims.put("id", user.getId());
        claims.put("authorities", user.getRole());
        claims.put("role", user.getRole());
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        long expirationTimeLong = Long.parseLong(expirationTime); //in second

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
