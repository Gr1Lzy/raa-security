package gitlab.rightandaboce.raasecurity.util.jwt;

import gitlab.rightandaboce.raasecurity.model.user.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;

    @Value("${jwt.lifetime}")
    private Duration jwtExpiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKeyString) {
        this.secretKey = new SecretKeySpec(secretKeyString.getBytes(), "HMACSHA256");
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration.toMillis()))
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
