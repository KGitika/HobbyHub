package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Hobby;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.repository.HobbyRepository;
import com.hobbyhub.hobbyhub.repository.TagRepository;
import com.hobbyhub.hobbyhub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private HobbyRepository hobbyRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
    }

    @Test
    void getUserHobbies_returnsNames() {
        Hobby hobby = new Hobby("Photography", "desc");
        User user = new User("Alex", "alex@example.com", "pwd");
        user.setId(1L);
        user.getHobbies().add(hobby);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<String> hobbies = userService.getUserHobbies(1L);
        assertEquals(List.of("Photography"), hobbies);
    }

    @Test
    void addHobbiesToUser_updatesHobbies() {
        User user = new User("Alex", "alex@example.com", "pwd");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hobbyRepository.findByName("Photography")).thenReturn(Optional.of(new Hobby("Photography", "desc")));

        boolean ok = userService.addHobbiesToUser(1L, List.of("Photography"));
        assertTrue(ok);
        assertEquals(1, user.getHobbies().size());
        verify(userRepository).save(user);
    }
}
