package tech.jamersondev.gratitude.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.jamersondev.gratitude.core.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${token.security.jwt}")
    private String securityJwt;

    public String generateToken(User user){
        return JWT.create().withIssuer("Gratitude App")
                .withSubject(user.getName()).withExpiresAt(dateExpiration())
                .sign(Algorithm.HMAC256(securityJwt));
    }

    public String getSubject(String tokenJwt){
        return JWT.require(Algorithm.HMAC256(securityJwt)).withIssuer("Gratitude App")
                .build().verify(tokenJwt).getSubject();
    }

    private Instant dateExpiration(){
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }
}
