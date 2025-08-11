package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.dto.HobbiesRequest;
import com.hobbyhub.hobbyhub.dto.RecommendationDTO;
import com.hobbyhub.hobbyhub.dto.UserDto;
import com.hobbyhub.hobbyhub.dto.UserIdDTO;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.toDto(user);
    }

    // existing endpoints retained for backward compatibility
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserIdDTO createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return new UserIdDTO(created.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updated = userService.updateUser(id, user);
            updated.setPassword(null);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/hobbies")
    public List<String> getUserHobbies(@PathVariable Long id) {
        return userService.getUserHobbies(id);
    }

    @PostMapping("/{id}/hobbies")
    public ResponseEntity<java.util.Map<String, Boolean>> addHobbiesToUser(@PathVariable Long id, @RequestBody HobbiesRequest request) {
        boolean success = userService.addHobbiesToUser(id, request.getHobbies());
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(java.util.Map.of("ok", true));
    }

    @GetMapping("/{id}/recommendations")
    public List<RecommendationDTO> getRecommendations(@PathVariable Long id) {
        return userService.recommendHobbies(id);
    }
}

