package api.service.implementations;

import api.dto.get.Pagination;
import api.entity.Booking;
import api.entity.User;
import api.repository.UserRepository;
import api.service.BookingService;
import api.service.UserService;
import api.util.Images;
import api.util.exception.UserNotFoundException;
import api.util.exception.WrongPasswordException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookingService bookingService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BookingService bookingService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bookingService = bookingService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        if(userRepository.findById(email).isPresent()) {
            return userRepository.findById(email).get();
        } else {
            throw new UserNotFoundException("User with this email not found");
        }
    }

    @Override
    public Page<Booking> getUserBookings(User user, Pagination pagination) {
        return bookingService.getUserBookings(user, LocalDate.now(), PageRequest.of(pagination.getPage(), pagination.getSize()));
    }

    @Override
    public User update(User user) {
        if(userRepository.existsById(user.getEmail())) {
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User with this email not found");
        }
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword) {
        if(passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new WrongPasswordException("Password does not match");
        }
    }

    @Override
    public Path updateImage(User user, MultipartFile image) throws IOException {
        String newPath = Images.saveImage(image);
        if(user.getImage() != null) {
            Files.deleteIfExists(Paths.get(user.getImage()));
        }
        user.setImage(newPath);
        userRepository.save(user);
        return Paths.get(user.getImage()).normalize();
    }

    @Override
    public void deleteUser(String email) {
        if(userRepository.existsById(email)) {
            userRepository.deleteById(email);
        } else {
            throw new UserNotFoundException("User with this email not found");
        }
    }

}
