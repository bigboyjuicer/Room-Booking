package api.service;

import api.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    User update(User user);

    void changePassword(User user, String oldPassword, String newPassword);

    Path updateImage(User user, MultipartFile image) throws IOException;

    void deleteUser(String email);
}
