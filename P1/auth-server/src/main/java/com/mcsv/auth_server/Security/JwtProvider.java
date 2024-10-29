package com.mcsv.auth_server.Security;

import com.mcsv.auth_server.Dto.RequestDto;
import com.mcsv.auth_server.Entity.AuthUser;
import com.mcsv.usuario_service.Model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    /*@Value("${jwt.secret}")
    private String secret;*/

    private Key secret;

    @PostConstruct
    protected void init(){
        byte[] apiKeySecretBytes = new byte[64]; // 512 bits
        new SecureRandom().nextBytes(apiKeySecretBytes);
        secret = Keys.hmacShaKeyFor(apiKeySecretBytes);
    }

    public static Date calcularExpiracion(int dias, int horas, int minutos, int segundos, int milisegundos) {
        long ahora = System.currentTimeMillis(); // Obtener el tiempo actual en milisegundos
        long tiempoDeExpiracion = (dias * 86400000L) // 86400000 milisegundos en un día
                + (horas * 3600000L) // 3600000 milisegundos en una hora
                + (minutos * 60000L) // 60000 milisegundos en un minuto
                + (segundos * 1000L) // 1000 milisegundos en un segundo
                + milisegundos; // Agregar milisegundos

        return new Date(ahora + tiempoDeExpiracion); // Crear y retornar la fecha de expiración
    }

    public String createToken(Usuario authUser){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",authUser.getId());
        claims.put("nombre",authUser.getNombre());
        claims.put("apellido",authUser.getApellido());
        claims.put("email",authUser.getEmail());

        // Fecha de emisión y expiración del token
        Date now = new Date();
        // funcion que me permite configurar dias, horas, minutos, segundos, milisegundos

        Date exp = calcularExpiracion(0, 1, 0, 0, 0); // 1 hora de validez

        return Jwts.builder()
                .claims(claims)
                .subject(authUser.getEmail())
                .issuedAt(now)
                .expiration(exp)
                .signWith(Keys.hmacShaKeyFor(secret.getEncoded()))
                .compact();
    }

    public boolean validate(String token){
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getEncoded())).build().parseSignedClaims(token);
        }catch (Exception exception){
            return false;
        }
        return true;
    }

    public String getUserNameFromToken(String token){
        try{
            return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getEncoded()))
                    .build()
                    .parseSignedClaims(token).getPayload().getSubject();
        }catch (Exception exception){
            throw new RuntimeException("Error al obtener el usuario del token");
        }
    }
}
