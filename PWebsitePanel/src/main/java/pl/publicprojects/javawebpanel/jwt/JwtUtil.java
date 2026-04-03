package pl.publicprojects.javawebpanel.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import pl.publicprojects.javawebpanel.session.SessionInfo;

import java.security.Key;
import java.util.Date;

@Getter
@Component
public class JwtUtil {

    @Value(value = "${public-projects.web-panel.jwt-key}")
    private String jwtKey;
    @Value(value = "${public-projects.web-panel.jwt-name}")
    private String jwtName;
    @Value(value = "${public-projects.web-panel.jwt-time}")
    private long jwtTime;
    @Value(value = "${public-projects.web-panel.jwt-secure-property}")
    private boolean secure;

    private Key signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtKey));
    }

    public ResponseCookie generateJwtCookie(SessionInfo sessionInfo) {
        Date date = new Date();

        String userNameToken = Jwts.builder().setSubject(sessionInfo.getUsername())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + this.jwtTime * 1000))
                .signWith(this.signingKey, SignatureAlgorithm.HS256)
                .compact();

        return ResponseCookie.from(this.jwtName, userNameToken).path("/api")
                .maxAge(this.jwtTime)
                .httpOnly(true)
                .secure(this.secure)
                .build();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
