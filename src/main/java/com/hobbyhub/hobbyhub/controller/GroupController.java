package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.entity.Event;
import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.service.EventService;
import com.hobbyhub.hobbyhub.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final EventService eventService;

    public GroupController(GroupService groupService, EventService eventService) {
        this.groupService = groupService;
        this.eventService = eventService;
    }

    @GetMapping("/{hobbyId}")
    public List<Group> getGroupsForHobby(@PathVariable Long hobbyId) {
        return groupService.getGroupsForHobby(hobbyId);
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<Void> joinGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        groupService.joinGroup(groupId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{groupId}/events")
    public List<Event> getEvents(@PathVariable Long groupId) {
        return eventService.getEventsForGroup(groupId);
    }
}