package api.controller;

import api.dto.LoginUserDto;
import api.dto.RegisterUserDto;
import api.dto.UserDto;
import api.entity.User;
import api.service.AuthenticationService;
import api.service.JWTService;
import api.util.Response;
import api.util.exception.RefreshTokenNotValidException;
import api.util.mapper.LoginUserMapper;
import api.util.mapper.RegisterUserMapper;
import api.util.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
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
    public Response signup(@RequestBody RegisterUserDto registerUserDto) {
        UserDto registeredUser = UserMapper.MAPPER.fromUser(authenticationService.signup(RegisterUserMapper.MAPPER.toUser(registerUserDto)));
        return new Response(true, "Successfully registered", new HashMap<>() {{ put("user", registeredUser); }}, null);
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(LoginUserMapper.MAPPER.toUser(loginUserDto));
        String accessToken = jwtService.generateAccessToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", accessToken);
        data.put("refreshToken", refreshToken);

        return new Response(true, "Successfully logged in", data, null);
    }

    @GetMapping("/refresh")
    public Response refresh(HttpServletRequest request) {
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

                    return new Response(true, "Successfully refreshed", data, null);
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
