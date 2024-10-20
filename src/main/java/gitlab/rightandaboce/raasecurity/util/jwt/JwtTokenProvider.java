package gitlab.rightandaboce.raasecurity.util.jwt;

import gitlab.rightandaboce.raasecurity.model.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtTokenProvider {
    private final SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKeyString) {
        this.secretKey = generateSecretKey(secretKeyString);
    }

    private SecretKey generateSecretKey(String secretKeyString) {
        return new SecretKeySpec(secretKeyString.getBytes(), "HMACSHA256");
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(secretKey)
                .compact();
    }
}
