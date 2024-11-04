package api.service;

import api.entity.User;
import api.repository.UserRepository;
import api.util.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if(userRepository.existsById(user.getEmail())) {
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User with this email not found");
        }
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
