package com.ecommercetech.security.jwt;

import org.springframework.stereotype.Component;
import com.ecommercetech.user.model.User;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


// Clase para manejar los token JWT(utilidad de seguridad)
// JwtUtil es el que fabrica y valida las llaves digitales(tokens) que permiten entrar al sistema

@Component
public class JwtUtil {

    // Clave secreta para firmar los token y evitar que sean manipulados
    private final String SECRET_KEY = "L123ANGEL";

    // Tiempo de vida del token
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora


    /*
    * Crea un token firmado que contiene:
    * EL username como subeto(sub)
    * El role como claim adicional
    * Fecha de creacion(iat) y expiracion(exp)
    * Firma con la clave secreta (SECRET_KEY)
    */
    public String generateToken(User user) {

        // Inicia la construccion del token
        return Jwts.builder()
                // Guarda el username como el "dueño" del token(sub)
                .setSubject(user.getUsername())
                // Agrega un datoe extra
                .claim("role", user.getRole()) // incluir rol en el token
                // Fecha de creacion(iat)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Fecha de expiracion(exp)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                // Firma digital con algoritmo HS256 y la clave secreta
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                // Devuelve el token como un string
                .compact();
    }


    // Extrae el username del token
    public String getUsernameFromToken(String token) {
        // Inicia el analisis del token
        return Jwts.parser()
                // Usa la clave secreta para verificar que el token no fue manipulado
                .setSigningKey(SECRET_KEY)
                // Decodifica el token y obtiene sus datos(claims)
                .parseClaimsJws(token)
                .getBody()
                // Devuelve el username que se guardo en el token
                .getSubject();

        /* Esto se usa en filtros de seguridad para saber que usuario esta haciendo la peticion*/
    }

    // Comprueba que el token no este expirado ni manipulado
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Revisa si la fecha de expiracin ya paso
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}

