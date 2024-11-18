package api.service;

import api.entity.User;

public interface JWTService {
    String generateToken(User user);
}
