package api.service;

import api.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken save(RefreshToken refreshToken);

    RefreshToken findByEmail(String email);
}
