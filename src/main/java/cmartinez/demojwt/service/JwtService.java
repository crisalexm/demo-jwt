package cmartinez.demojwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class JwtService {

    @Value("${SECRET_KEY}")
    String secretKey;


    public String generateToken(String email) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
//        120000 2 min, 3600000 1 hora
        long expMillis = nowMillis + 120000;


        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(expMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}