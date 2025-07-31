package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Hobby;
import com.hobbyhub.hobbyhub.entity.Tag;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.repository.HobbyRepository;
import com.hobbyhub.hobbyhub.repository.TagRepository;
import com.hobbyhub.hobbyhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.HashSet;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final TagRepository tagRepository;

    // Constructor injection
    public UserService(UserRepository userRepository, HobbyRepository hobbyRepository, TagRepository tagRepository)  {
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
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<Hobby> getUserHobbies(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return List.copyOf(user.getHobbies());
    }

    public void addHobbiesToUser(Long userId, List<Long> hobbyIds) {
        User user = userRepository.findById(userId).orElseThrow();
        for (Long hid : hobbyIds) {
            Hobby hobby = hobbyRepository.findById(hid).orElseThrow();
            user.getHobbies().add(hobby);
        }
        userRepository.save(user);
    }

    public List<Hobby> recommendHobbies(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        // collect tags of user's hobbies
        var tags = new HashSet<Tag>();
        for (Hobby hobby : user.getHobbies()) {
            tags.addAll(hobby.getTags());
        }
        // find hobbies with those tags excluding already added hobbies
        List<Hobby> all = hobbyRepository.findAll();
        List<Hobby> result = new java.util.ArrayList<>();
        for (Hobby h : all) {
            if (!user.getHobbies().contains(h)) {
                for (Tag t : h.getTags()) {
                    if (tags.contains(t)) {
                        result.add(h);
                        break;
                    }
                }
            }
        }
        return result;
    }
}
