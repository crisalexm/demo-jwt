package cmartinez.demojwt.service.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
@Service
public class JwtUtil {

//    @Value("${JWT_SECRET_KEY}")
//    private String secretKey;
    // Esto debe estar en un lugar seguro y no estar codificado

    public String generateToken(String username) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // Generar una clave segura de 256 bits
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        String secretKey = Base64.getEncoder().encodeToString(keyBytes);


        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}

