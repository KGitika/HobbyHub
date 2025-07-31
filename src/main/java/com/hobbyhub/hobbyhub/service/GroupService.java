package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.entity.Hobby;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.repository.GroupRepository;
import com.hobbyhub.hobbyhub.repository.HobbyRepository;
import com.hobbyhub.hobbyhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final HobbyRepository hobbyRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, HobbyRepository hobbyRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.hobbyRepository = hobbyRepository;
        this.userRepository = userRepository;
    }

    public List<Group> getGroupsForHobby(Long hobbyId) {
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow();
        return groupRepository.findByHobby(hobby);
    }

    public void joinGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        group.getMembers().add(user);
        user.getGroups().add(group);
        groupRepository.save(group);
        userRepository.save(user);
    }
}

