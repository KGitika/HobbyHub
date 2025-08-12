package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.dto.LoginResponse;
import com.hobbyhub.hobbyhub.dto.SignInRequest;
import com.hobbyhub.hobbyhub.dto.SignUpRequest;
import com.hobbyhub.hobbyhub.dto.UserDto;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.service.JwtService;
import com.hobbyhub.hobbyhub.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        logger.info("Signup request for email {}", request.getEmail());
        try {
            User user = new User(request.getName(), request.getEmail(), request.getPassword());
            User created = userService.createUser(user);
            return ResponseEntity.status(201).body(java.util.Map.of("id", created.getId()));
        } catch (Exception ex) {
            logger.error("Error during signup for email {}", request.getEmail(), ex);
            throw ex;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInRequest request) {
        logger.info("Login attempt for email {}", request.getEmail());
        return userService.authenticate(request.getEmail(), request.getPassword())
                .map(u -> {
                    String token = jwtService.generateToken(u.getId().toString());
                    UserDto dto = userService.toDto(u);
                    return new LoginResponse(token, dto);
                })
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Invalid credentials for email {}", request.getEmail());
                    return ResponseEntity.status(401)
                            .body(java.util.Map.of("error", "Invalid credentials"));
                });
    }
}

