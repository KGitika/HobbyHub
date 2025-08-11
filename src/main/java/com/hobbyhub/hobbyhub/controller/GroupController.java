package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.dto.CreateGroupRequest;
import com.hobbyhub.hobbyhub.dto.MembershipResponse;
import com.hobbyhub.hobbyhub.entity.Event;
import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.service.EventService;
import com.hobbyhub.hobbyhub.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final EventService eventService;

    public GroupController(GroupService groupService, EventService eventService) {
        this.groupService = groupService;
        this.eventService = eventService;
    }

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody CreateGroupRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Group created = groupService.createGroup(request.getName(), request.getHobby(), request.getDescription(), user);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/hobby/{hobbyId}")
    public List<Group> getGroupsForHobby(@PathVariable Long hobbyId) {
        return groupService.getGroupsForHobby(hobbyId);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroup(@PathVariable Long groupId) {
        return groupService.getGroup(groupId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<Void> joinGroup(@PathVariable Long groupId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        groupService.joinGroup(groupId, user.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/leave")
    public MembershipResponse leaveGroup(@PathVariable Long groupId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        groupService.leaveGroup(groupId, user.getId());
        return new MembershipResponse(false);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Map<String, Boolean>> deleteGroup(@PathVariable Long groupId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        groupService.deleteGroup(groupId, user.getId());
        return ResponseEntity.ok(Map.of("ok", true));
    }

    @GetMapping("/{groupId}/events")
    public List<Event> getEvents(@PathVariable Long groupId) {
        return eventService.getEventsForGroup(groupId);
    }
}
