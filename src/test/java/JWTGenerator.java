import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JWTGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generuje bezpieczny klucz
        String base64Key = Encoders.BASE64.encode(key.getEncoded()); // Koduje do Base64
        System.out.println("Twój klucz: " + base64Key);
    }
}
