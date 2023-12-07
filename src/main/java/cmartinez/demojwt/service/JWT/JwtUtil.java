package cmartinez.demojwt.service.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
@Service
public class JwtUtil {

    @Value("${SECRET_KEY}")
    String secretKey;


    public String generateToken(String email) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + 3600000;
        // Generar una clave segura de 256 bits
//        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
//        String secretKey = Base64.getEncoder().encodeToString(keyBytes);


        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(expMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}

