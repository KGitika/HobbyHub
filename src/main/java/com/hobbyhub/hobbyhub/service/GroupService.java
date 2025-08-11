package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.entity.Hobby;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.repository.GroupRepository;
import com.hobbyhub.hobbyhub.repository.HobbyRepository;
import com.hobbyhub.hobbyhub.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public void joinGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        group.getMembers().add(user);
        user.getGroups().add(group);
        groupRepository.save(group);
        userRepository.save(user);
    }

    @Transactional
    public void leaveGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        group.getMembers().remove(user);
        user.getGroups().remove(group);
        groupRepository.save(group);
        userRepository.save(user);
    }

    public java.util.Optional<Group> getGroup(Long id) {
        return groupRepository.findById(id);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Transactional
    public Group createGroup(String name, String hobbyName, String description, User creator) {
        Hobby hobby = hobbyRepository.findByName(hobbyName).orElseThrow();
        User creatorEntity = userRepository.findById(creator.getId()).orElseThrow();
        Group group = new Group(name, description, hobby);
        group.setOwner(creatorEntity);
        group.getMembers().add(creatorEntity);
        creatorEntity.getGroups().add(group);
        Group saved = groupRepository.save(group);
        userRepository.save(creatorEntity);
        return saved;
    }

    @Transactional
    public void deleteGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        if (group.getOwner() == null || !group.getOwner().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the owner may delete this group");
        }
        for (User member : group.getMembers()) {
            member.getGroups().remove(group);
            userRepository.save(member);
        }
        group.getMembers().clear();
        groupRepository.delete(group);
    }
}
