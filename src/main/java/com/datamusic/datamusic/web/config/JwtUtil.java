package com.datamusic.datamusic.web.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtil {
    
    private static String SECRET_KEY = "245Sl4y3r#";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String create(String username) {
        //    Map<String, Object> colorFrequencyValues = new HashMap<String, Object>();
                // colorFrequencyValues.put("user","xd");
        return JWT.create()
                // .withPayload(colorFrequencyValues)
                .withSubject(username)
                .withIssuer("dataMusic")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .sign(ALGORITHM);
    }

    public boolean isValid(String jwt) {
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt);
            return true;
            //excepcion que me retorna el metodo verify si el token no es valido JWTVerificationException
        } catch (JWTVerificationException e) {
            return false;
        }

    }

    public String getUsername(String jwt){
       return  JWT.require(ALGORITHM)
        .build()
        .verify(jwt)
        .getSubject();
    }
}
