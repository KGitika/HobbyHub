package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.controller.AuthController;
import com.hobbyhub.hobbyhub.entity.Hobby;
import com.hobbyhub.hobbyhub.entity.Tag;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.dto.RecommendationDTO;
import com.hobbyhub.hobbyhub.dto.UserDto;
import com.hobbyhub.hobbyhub.repository.HobbyRepository;
import com.hobbyhub.hobbyhub.repository.TagRepository;
import com.hobbyhub.hobbyhub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.HashSet;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final TagRepository tagRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Constructor injection
    public UserService(UserRepository userRepository, HobbyRepository hobbyRepository, TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.hobbyRepository = hobbyRepository;
        this.tagRepository = tagRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    if (updatedUser.getPassword() != null) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<String> getUserHobbies(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getHobbies().stream()
                .map(Hobby::getName)
                .toList();
    }

    public boolean addHobbiesToUser(Long userId, List<String> hobbyNames) {
        var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return false;
        }
        updateInterests(userId, hobbyNames);
        return true;
    }

    public List<RecommendationDTO> recommendHobbies(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        // collect tags of user's hobbies
        var tags = new HashSet<Tag>();
        for (Hobby hobby : user.getHobbies()) {
            tags.addAll(hobby.getTags());
        }
        // find hobbies with those tags excluding already added hobbies
        List<Hobby> all = hobbyRepository.findAll();
        List<RecommendationDTO> result = new java.util.ArrayList<>();
        for (Hobby h : all) {
            if (!user.getHobbies().contains(h)) {
                for (Tag t : h.getTags()) {
                    if (tags.contains(t)) {
                        result.add(new RecommendationDTO("🎯", h.getName(), 95));
                        break;
                    }
                }
            }
        }
        return result;
    }

    public Optional<User> authenticate(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(rawPassword, u.getPassword()));
    }

    public UserDto toDto(User user) {
        logger.info("Printing User: {}", user.getName());
        User loadUser = userRepository.findById(user.getId()).orElseThrow();
        java.util.List<String> interests = loadUser.getHobbies().stream()
                .map(Hobby::getName)
                .toList();
        return new UserDto(loadUser.getId(), loadUser.getName(), loadUser.getEmail(), interests);
    }

    public void updateInterests(Long userId, java.util.List<String> interests) {
        User user = userRepository.findById(userId).orElseThrow();
        user.getHobbies().clear();
        for (String name : interests) {
            hobbyRepository.findByName(name).ifPresent(h -> user.getHobbies().add(h));
        }
        userRepository.save(user);
    }
}
