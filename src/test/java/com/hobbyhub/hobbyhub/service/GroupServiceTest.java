package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.entity.Hobby;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.repository.GroupRepository;
import com.hobbyhub.hobbyhub.repository.HobbyRepository;
import com.hobbyhub.hobbyhub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;
    @Mock
    private HobbyRepository hobbyRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    void getAllGroups_returnsGroups() {
        List<Group> groups = List.of(new Group());
        when(groupRepository.findAll()).thenReturn(groups);

        List<Group> result = groupService.getAllGroups();
        assertEquals(groups, result);
        verify(groupRepository).findAll();
    }

    @Test
    void createGroup_addsCreatorAndSaves() {
        User user = new User();
        Hobby hobby = new Hobby();
        when(hobbyRepository.findByName("Photography")).thenReturn(java.util.Optional.of(hobby));
        when(groupRepository.save(any(Group.class))).thenAnswer(inv -> inv.getArgument(0));

        Group result = groupService.createGroup("Astro", "Photography", "Desc", user);

        assertEquals("Astro", result.getTitle());
        assertTrue(result.getMembers().contains(user));
        verify(groupRepository).save(any(Group.class));
        verify(userRepository).save(user);
    }

    @Test
    void leaveGroup_removesMembership() {
        Group group = new Group();
        User user = new User();
        group.getMembers().add(user);
        user.getGroups().add(group);
        when(groupRepository.findById(1L)).thenReturn(java.util.Optional.of(group));
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        groupService.leaveGroup(1L, 1L);

        assertFalse(group.getMembers().contains(user));
        assertFalse(user.getGroups().contains(group));
        verify(groupRepository).save(group);
        verify(userRepository).save(user);
    }
}
