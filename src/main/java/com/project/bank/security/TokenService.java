package com.project.bank.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.bank.entity.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService
{
    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(Usuario usuario)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("bank-api")
                    .withSubject(usuario.getCpf())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        }
        catch (JWTCreationException ex)
        {
            throw new RuntimeException("Erro ao gerar token", ex);
        }
    }

    public String validateToken(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("bank-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException ex)
        {
               return "";
        }

    }

    private Instant genExpirationDate()
    {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); //adiciona duas horas em relação a hora atual
    }
}
