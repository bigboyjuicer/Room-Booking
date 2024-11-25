package api.controller;

import api.dto.UserDto;
import api.entity.User;
import api.service.UserService;
import api.util.MyCustomResponse;
import api.util.mapper.UserMapper;
import api.util.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Secured("ADMIN")
    public ResponseEntity<MyCustomResponse> getAllUsers() {
        //List<User> users = userService.getAllUsers();
        List<UserDto> users = UserMapper.MAPPER.fromUsers(userService.getAllUsers());
        if (users.isEmpty()) {
            return ResponseEntity.ok().body(new MyCustomResponse(true, "There are no users", null, null));
        } else {
            Map<String, Object> data = new HashMap<>() {{
                put("users", users);
            }};
            return ResponseEntity.ok().body(new MyCustomResponse(true, "Users successfully found", data, null));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<MyCustomResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto currentUser = UserMapper.MAPPER.fromUser((User) authentication.getPrincipal());
        return ResponseEntity.ok().body(new MyCustomResponse(true, "Successfully got current user", new HashMap<>() {{ put("user", currentUser); }}, null));
    }

    @DeleteMapping("/me/delete")
    public ResponseEntity<MyCustomResponse> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        userService.deleteUser(currentUser.getEmail());
        return ResponseEntity.ok().body(new MyCustomResponse(true, "User successfully deleted", null, null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MyCustomResponse> handleRoomNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.badRequest().body(new MyCustomResponse(false, ex.getMessage(), null, new HashMap<>() {{
            put("email", "Not found");
        }}));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MyCustomResponse> handleBadCredentialsException() {
        return new ResponseEntity<>(new MyCustomResponse(false, "Invalid email or password", null, null), HttpStatus.UNAUTHORIZED);
    }

}
