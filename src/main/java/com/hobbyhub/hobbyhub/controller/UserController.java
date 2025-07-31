package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.entity.Hobby;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {

    //    @GetMapping("/users")
//    public List<String> getUsers() {
//        return List.of("Alice", "Bob", "Charlie");
//    }
    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updated = userService.updateUser(id, user);
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
    public List<Hobby> getUserHobbies(@PathVariable Long id) {
        return userService.getUserHobbies(id);
    }

    @PostMapping("/{id}/hobbies")
    public ResponseEntity<Void> addHobbiesToUser(@PathVariable Long id, @RequestBody List<Long> hobbyIds) {
        userService.addHobbiesToUser(id, hobbyIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/recommendations")
    public List<Hobby> getRecommendations(@PathVariable Long id) {
        return userService.recommendHobbies(id);
    }
}