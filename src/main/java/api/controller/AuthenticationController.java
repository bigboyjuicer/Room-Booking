package api.controller;

import api.dto.post.LoginUserDto;
import api.dto.post.RegisterUserDto;
import api.dto.get.UserProfileDto;
import api.entity.User;
import api.service.AuthenticationService;
import api.service.JWTService;
import api.util.ApiResponse;
import api.util.exception.RefreshTokenNotValidException;
import api.util.mapper.LoginUserMapper;
import api.util.mapper.RegisterUserMapper;
import api.util.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JWTService jwtService;

    private final AuthenticationService authenticationService;

    private final UserDetailsService userDetailsService;

    public AuthenticationController(JWTService jwtService, AuthenticationService authenticationService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody RegisterUserDto registerUserDto) {
        UserProfileDto registeredUser = UserMapper.MAPPER.fromUser(authenticationService.signup(RegisterUserMapper.MAPPER.toUser(registerUserDto)));
        return ResponseEntity.ok().body(new ApiResponse(true, "Successfully registered", new HashMap<>() {{ put("user", registeredUser); }}, null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(LoginUserMapper.MAPPER.toUser(loginUserDto));
        String accessToken = jwtService.generateAccessToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", accessToken);
        data.put("refreshToken", refreshToken);

        return ResponseEntity.ok().body(new ApiResponse(true, "Successfully logged in", data, null));
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse> refresh(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        if(refreshToken != null) {
            String userEmail = jwtService.extractEmail(refreshToken);
            if(userEmail != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                if(jwtService.isRefreshTokenValid(refreshToken, userDetails)) {
                    String accessToken = jwtService.generateAccessToken(userDetails);
                    refreshToken = jwtService.generateRefreshToken(userDetails);

                    Map<String, Object> data = new HashMap<>();
                    data.put("accessToken", accessToken);
                    data.put("refreshToken", refreshToken);

                    return ResponseEntity.ok().body(new ApiResponse(true, "Successfully refreshed", data, null));
                } else {
                    throw new RefreshTokenNotValidException("Refresh token is not valid");
                }
            } else {
                throw new RefreshTokenNotValidException("Refresh token is not valid");
            }
        } else {
            throw new RefreshTokenNotValidException("Cannot find refresh token");
        }

    }

}
