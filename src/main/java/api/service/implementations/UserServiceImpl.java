package api.service.implementations;

import api.entity.User;
import api.repository.UserRepository;
import api.service.UserService;
import api.util.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    public void deleteUser(String email) {
        if(userRepository.existsById(email)) {
            userRepository.deleteById(email);
        } else {
            throw new UserNotFoundException("User with this email not found");
        }
    }

}
