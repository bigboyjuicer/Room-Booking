package api.service;

import api.entity.User;
import api.repository.UserRepository;
import api.util.exception.UserNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final JWTServiceImpl jwtServiceImpl;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JWTServiceImpl jwtServiceImpl) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtServiceImpl = jwtServiceImpl;
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
    public boolean checkUser(User user) {
        if (userRepository.findById(user.getEmail()).isPresent()) {
            return userRepository.findById(user.getEmail()).get().getPassword().equals(user.getPassword());
        } else {
            return false;
        }
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public String verifyUser(User user){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(auth.isAuthenticated()) {
            return jwtServiceImpl.generateToken(user);
        }
        return "Failure";
    }
}
