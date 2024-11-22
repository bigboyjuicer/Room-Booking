package api.service;

import api.entity.User;

public interface AuthenticationService {

    User signup(User input);

    User authenticate(User input);
}
