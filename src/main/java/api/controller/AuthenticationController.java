package api.controller;

import api.dto.LoginUserDto;
import api.dto.RegisterUserDto;
import api.dto.UserDto;
import api.entity.User;
import api.service.AuthenticationService;
import api.service.JWTService;
import api.util.Response;
import api.util.mapper.LoginUserMapper;
import api.util.mapper.RegisterUserMapper;
import api.util.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JWTService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JWTService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public Response signup(@RequestBody RegisterUserDto registerUserDto) {
        UserDto registeredUser = UserMapper.MAPPER.fromUser(authenticationService.signup(RegisterUserMapper.MAPPER.toUser(registerUserDto)));
        return new Response(true, "Successfully registered", new HashMap<>() {{ put("user", registeredUser); }}, null);
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(LoginUserMapper.MAPPER.toUser(loginUserDto));
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return new Response(true, "Successfully logged in", new HashMap<>() {{ put("token", jwtToken); }}, null);
    }

}
