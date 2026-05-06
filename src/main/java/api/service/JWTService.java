package api.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JWTService {

    String extractEmail(String token, String type);

    <T> T extractClaim(String token, String type, Function<Claims, T> claimsResolver);

    String generateAccessToken(UserDetails userDetails);

    String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expirationTime, String type);

    boolean isAccessTokenValid(String token, UserDetails userDetails);

    boolean isRefreshTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token, String type);

    Date extractExpiration(String token, String type);

    Claims extractAllClaims(String token, String type);

    SecretKey getSignInKey(String secretKey);
}
