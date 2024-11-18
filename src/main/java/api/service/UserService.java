package api.service;

import api.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserByEmail(String email);

    boolean checkUser(User user);

    User registerUser(User user);

    User updateUser(User user);

    void deleteUser(String email);

    String verifyUser(User user);
}
