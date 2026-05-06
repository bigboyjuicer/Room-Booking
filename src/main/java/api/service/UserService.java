package api.service;

import api.dto.get.Pagination;
import api.entity.Booking;
import api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    Page<Booking> getUserBookings(User user, Pagination pagination);

    User update(User user);

    void changePassword(User user, String oldPassword, String newPassword);

    String updateImage(User user, MultipartFile image) throws IOException;

    void deleteUser(String email);
}
