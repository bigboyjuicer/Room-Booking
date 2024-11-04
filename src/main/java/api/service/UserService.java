package api.service;

import api.entity.User;
import api.util.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserByEmail(String email);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(String email);

}
