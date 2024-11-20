package api.service;

import api.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JWTService {

    String extractEmail(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    long getExpirationTime();

    String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expirationTime);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    Claims extractAllClaims(String token);

    SecretKey getSignInKey();
}
