package api.controller;

import api.dto.UserDTO;
import api.entity.User;
import api.service.UserService;
import api.util.Response;
import api.util.UserMapper;
import api.util.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Response getAllUsers() {
        List<UserDTO> users = UserMapper.MAPPER.fromUsers(userService.getAllUsers());
        if(users.isEmpty()) {
            return new Response(true, "There are no users", null, null);
        } else {
            Map<String, Object> data = new HashMap<>() {{
                put("users", users);
            }};
            return new Response(true, "Users successfully found", data, null);
        }
    }

    @GetMapping("/{email}")
    public Response getUserByEmail(@PathVariable String email) {
        UserDTO user = UserMapper.MAPPER.fromUser(userService.getUserByEmail(email));
        Map<String, Object> data = new HashMap<>() {{
            put("user", user);
        }};
        return new Response(true, "User successfully found", data, null);
    }

    @PostMapping
    public Response addUser(@Valid @RequestBody User user) {
        User newUser = userService.saveUser(user);
        Map<String, Object> data = new HashMap<>() {{
            put("user", newUser);
        }};
        return new Response(true, "User successfully added", data, null);
    }

    @DeleteMapping("/{email}")
    public Response deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return new Response(true, "User successfully deleted", null, null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleRoomNotFoundException(UserNotFoundException ex) {
        return new Response(false, ex.getMessage(), null, new HashMap<>() {{put("email", "Not found");}});
    }

}
