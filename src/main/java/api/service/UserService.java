package api.service;

import api.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    void deleteUser(String email);
}
