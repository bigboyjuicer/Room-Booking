package api.controller;

import api.dto.get.Pagination;
import api.dto.get.UserProfileDto;
import api.dto.put.ChangePassword;
import api.dto.put.UserUpdateDto;
import api.entity.Booking;
import api.entity.User;
import api.security.MyUserDetails;
import api.service.UserService;
import api.util.ApiResponse;
import api.util.MyPageable;
import api.util.exception.WrongPasswordException;
import api.util.mapper.BookingProfileMapper;
import api.util.mapper.UserMapper;
import api.util.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<UserProfileDto> users = UserMapper.MAPPER.fromUsers(userService.getAllUsers());
        if (users.isEmpty()) {
            return ResponseEntity.ok().body(new ApiResponse(true, "There are no users", null, null));
        } else {
            Map<String, Object> data = new HashMap<>() {{
                put("users", users);
            }};
            return ResponseEntity.ok().body(new ApiResponse(true, "Users successfully found", data, null));
        }
    }

    @Operation(summary = "Get current user info")
    @GetMapping("/me")
    @Secured("USER")
    public ResponseEntity<ApiResponse> getCurrentUser(Authentication authentication) {
        UserProfileDto currentUser = UserMapper.MAPPER.fromUser(((MyUserDetails) authentication.getPrincipal()).getUser());
        return ResponseEntity.ok().body(new ApiResponse(true, "Successfully got current user", new HashMap<>() {{
            put("user", currentUser);
        }}, null));
    }

    @GetMapping("/me/bookings")
    public ResponseEntity<ApiResponse> getBookings(Authentication authentication, @RequestBody Pagination pagination) {
        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();
        Page<Booking> bookings = userService.getUserBookings(currentUser, pagination);
        return ResponseEntity.ok().body(new ApiResponse(true, "Successfully got current user's bookings", new HashMap<>() {{
            put("bookings", BookingProfileMapper.MAPPER.fromBookings(bookings.getContent()));
            put("pagination", MyPageable.build(bookings));
        }}, null));
    }

    @Operation(summary = "Get current user image")
    @GetMapping("/me/image")
    @Secured("USER")
    public ResponseEntity<Resource> getCurrentUserImage(Authentication authentication) {
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        if(user.getImage() != null) {
            Path filePath = Paths.get(user.getImage()).normalize();
            Resource resource;

            try {
                resource = new UrlResource(filePath.toUri());
            } catch (MalformedURLException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update current user image")
    @PutMapping("/me/image")
    @Secured("USER")
    public ResponseEntity<Resource> updateImage(Authentication authentication, MultipartFile image) {
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();

        try {
            userService.updateImage(user, image);
            return getCurrentUserImage(authentication);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update current user")
    @PutMapping("/me")
    @Secured("USER")
    public ResponseEntity<ApiResponse> updateCurrentUser(@Valid @RequestBody UserUpdateDto updatedUser, Authentication authentication) {
        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

        if (currentUser.getEmail().equals(updatedUser.getEmail())) {

            currentUser.setFirstName(updatedUser.getFirstName());
            currentUser.setLastName(updatedUser.getLastName());
            currentUser.setDepartment(updatedUser.getDepartment());
            currentUser.setTheme(updatedUser.getTheme());

            userService.update(currentUser);
        } else {
            throw new BadCredentialsException("Wrong email");
        }

        return ResponseEntity.ok().body(new ApiResponse(true, "Successfully updated", new HashMap<>() {{
            put("user", updatedUser);
        }}, null));
    }

    @Operation(summary = "Change password")
    @PutMapping("/me/change-password")
    @Secured("USER")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePassword changePassword, Authentication authentication) {
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        userService.changePassword(user, changePassword.getOldPassword(), changePassword.getNewPassword());

        return ResponseEntity.ok().body(new ApiResponse(true, "Password successfully changed", null, null));
    }

    @Operation(summary = "Delete current user")
    @DeleteMapping("/me/delete")
    @Secured("USER")
    public ResponseEntity<ApiResponse> deleteUser(Authentication authentication) {
        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();
        userService.deleteUser(currentUser.getEmail());
        return ResponseEntity.ok().body(new ApiResponse(true, "User successfully deleted", null, null));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage(), null, new HashMap<>() {{
            put("email", "Not found");
        }}));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ApiResponse> handleWrongPasswordException(WrongPasswordException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage(), null, null));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException() {
        return new ResponseEntity<>(new ApiResponse(false, "Invalid email or password", null, null), HttpStatus.UNAUTHORIZED);
    }

}
