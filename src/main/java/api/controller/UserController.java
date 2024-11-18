package api.controller;

import api.dto.LoginDto;
import api.dto.UserDto;
import api.entity.User;
import api.service.UserService;
import api.util.Response;
import api.util.UserMapper;
import api.util.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public UserController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/users")
    @Secured("ADMIN")
    public Response getAllUsers() {
        //List<User> users = userService.getAllUsers();
        List<UserDto> users = UserMapper.MAPPER.fromUsers(userService.getAllUsers());
        if (users.isEmpty()) {
            return new Response(true, "There are no users", null, null);
        } else {
            Map<String, Object> data = new HashMap<>() {{
                put("users", users);
            }};
            return new Response(true, "Users successfully found", data, null);
        }
    }

    @GetMapping("users/{email}")
    public Response getUserByEmail(@PathVariable String email) {
        UserDto user = UserMapper.MAPPER.fromUser(userService.getUserByEmail(email));
        Map<String, Object> data = new HashMap<>() {{
            put("user", user);
        }};
        return new Response(true, "User successfully found", data, null);
    }

    @PostMapping("/login")
    public Response login(@Valid @RequestBody LoginDto loginDto) {
        User user = new User();
        user.setEmail(loginDto.getEmail());
        user.setPassword(loginDto.getPassword());

        return new Response(true, userService.verifyUser(user), null, null);
    }

    @PostMapping("/register")
    public Response register(@Valid @RequestBody User user) {
        UserDto newUser = UserMapper.MAPPER.fromUser(userService.registerUser(user));
        Map<String, Object> data = new HashMap<>() {{
            put("user", newUser);
        }};
        return new Response(true, "User successfully registered", data, null);
    }

    @DeleteMapping("users/{email}")
    public Response deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return new Response(true, "User successfully deleted", null, null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleRoomNotFoundException(UserNotFoundException ex) {
        return new Response(false, ex.getMessage(), null, new HashMap<>() {{
            put("email", "Not found");
        }});
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response handleBadCredentialsException() {
        return new Response(false, "Invalid email or password", null, null);
    }

}
