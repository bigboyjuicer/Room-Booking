package api.service;

import api.dto.LoginUserDto;
import api.dto.RegisterUserDto;
import api.entity.User;

public interface AuthenticationService {

    User signup(User input);

    User authenticate(User input);
}
