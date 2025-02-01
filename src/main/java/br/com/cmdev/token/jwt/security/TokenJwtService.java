package br.com.cmdev.token.jwt.security;

import br.com.cmdev.token.jwt.models.User;
import br.com.cmdev.token.jwt.models.dtos.TokenResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenJwtService {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${api.security.token.secret}")
    private String secret;


    public TokenResponse generateTokenJwt(User user) {
        Instant instantNow = Instant.now();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            return new TokenResponse(JWT.create()
                    .withIssuer(applicationName)
                    .withSubject(user.getEmail())
                    .withIssuedAt(instantNow)
                    .withExpiresAt(generateExpirationInstant())
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(instantNow.plus(1, ChronoUnit.SECONDS))
                    .sign(algorithm),
                    user.getEmail(), dateFormat.format(Date.from(instantNow)), dateFormat.format(Date.from(generateExpirationInstant())));
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error when try generate token", exception);
        }
    }

    public String validateTokenJwt(String tokenJwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            return JWT.require(algorithm)
                    .withIssuer(applicationName)
                    .build()
                    .verify(tokenJwt)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant generateExpirationInstant() {
        return LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.systemDefault().getRules().getOffset(Instant.now()));
    }

}
