package api.service.implementations;

import api.entity.RefreshToken;
import api.service.JWTService;
import api.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    private final RefreshTokenService refreshTokenService;

    @Value("${JWT_ACCESS_SECRET_KEY}")
    private final String accessSecretKey;

    @Value("${JWT_REFRESH_SECRET_KEY}")
    private final String refreshSecretKey;

    @Value("${JWT_ACCESS_EXPIRATION}")
    private final long accessExpiration;

    @Value("${JWT_REFRESH_EXPIRATION}")
    private final long refreshExpiration;

    public JWTServiceImpl(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
        this.accessSecretKey = "default";
        this.refreshSecretKey = "default";
        this.accessExpiration = 0;
        this.refreshExpiration = 0;
    }

    @Override
    public String extractEmail(String token, String type) {
        return extractClaim(token, type, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, String type, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token, type);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, accessExpiration, "Access");
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String token = buildToken(extraClaims, userDetails, refreshExpiration, "Refresh");
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setEmail(userDetails.getUsername());
        refreshToken.setRefreshToken(token);

        refreshTokenService.save(refreshToken);

        return token;
    }

    @Override
    public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expirationTime, String type) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(type.equals("Access") ? accessSecretKey : refreshSecretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        String username = extractEmail(token, "Access");
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, "Access"));
    }

    @Override
    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        RefreshToken refreshToken = refreshTokenService.findByEmail(userDetails.getUsername());
        return (refreshToken.getRefreshToken().equals(token) && !isTokenExpired(token, "Refresh"));
    }

    @Override
    public boolean isTokenExpired(String token, String type) {
        return extractExpiration(token, type).before(new Date());
    }

    @Override
    public Date extractExpiration(String token, String type) {
        return extractClaim(token, type, Claims::getExpiration);
    }

    @Override
    public Claims extractAllClaims(String token, String type) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(type.equals("Access") ? accessSecretKey : refreshSecretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public SecretKey getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
