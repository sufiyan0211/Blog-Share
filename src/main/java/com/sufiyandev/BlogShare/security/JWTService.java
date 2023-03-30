package com.sufiyandev.BlogShare.security;


import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;


import java.util.Date;

@Service
@PropertySource("classpath:pass.properties")
public class JWTService {

    private String key;

    private Algorithm algorithm;

    @Autowired
    public JWTService(@Value("${jwt.key}") String key) {
        this.key = key;
        this.algorithm = Algorithm.HMAC256(key);;
    }


    public String createJwt(Long userId) {
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                // .withExpiresAt() // TODO: setup and expiry parameter
                .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString) {
        var decodedJWT = JWT.decode(jwtString);
        var userId = Long.valueOf(decodedJWT.getSubject());
        return userId;
    }
}