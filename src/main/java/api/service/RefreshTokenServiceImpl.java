package api.service;

import api.entity.RefreshToken;
import api.repository.RefreshTokenRepository;
import api.util.exception.RefreshTokenNotValidException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByEmail(refreshToken.getEmail());
        refreshTokenOptional.ifPresent(token -> refreshToken.setId(token.getId()));

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken findByEmail(String email) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(email);
        if (refreshToken.isPresent()) {
            return refreshToken.get();
        } else {
            throw new RefreshTokenNotValidException("Cannot find refresh token by email: " + email);
        }
    }

}
