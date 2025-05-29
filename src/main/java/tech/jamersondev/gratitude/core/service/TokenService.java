package tech.jamersondev.gratitude.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.jamersondev.gratitude.core.interfaces.AuthTokenService;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.exceptions.UserNotFoundException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService implements AuthTokenService {

    @Value("${token.security.jwt}")
    private String securityJwt;

    private final UserRepository userRepository;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(User user){
        return JWT.create().withIssuer("Gratitude App")
                .withSubject(user.getEmail()).withExpiresAt(dateExpiration(1))
                .withClaim("type", "access")
                .sign(Algorithm.HMAC256(securityJwt));
    }

    public String getSubject(String tokenJwt){
        return JWT.require(Algorithm.HMAC256(securityJwt)).withIssuer("Gratitude App")
                .build().verify(tokenJwt).getSubject();
    }

    private Instant dateExpiration(int hours){
        return LocalDateTime.now().plusHours(hours).toInstant(ZoneOffset.of("-03:00"));
    }

    @Override
    public String refreshAccessToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(securityJwt)).withClaim("type", "refresh").build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String userEmail = decodedJWT.getSubject();

        User user = this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found with e-mail: %s", userEmail)));

        return this.generateToken(user);
    }

    public String generateRefreshToken(User user){
        return JWT.create().withIssuer("Gratitude App")
                .withSubject(user.getEmail()).withExpiresAt(dateExpiration(24*7))
                .withClaim("type", "refresh")
                .sign(Algorithm.HMAC256(securityJwt));
    }
}
