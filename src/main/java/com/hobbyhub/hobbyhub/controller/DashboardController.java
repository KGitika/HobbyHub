package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.dto.DashboardSummaryDTO;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public Map<String, Object> getUserDashboard(@RequestParam Long userId) {
        User user = userService.getUserById(userId).orElseThrow();
        int joinedGroups = user.getGroups().size();
        int upcomingEvents = (int) user.getRsvpedEvents().stream()
                .filter(e -> e.getDate().isAfter(OffsetDateTime.now()))
                .count();
        int newConnections = user.getGroups().stream().mapToInt(g -> g.getMembers().size()).sum() - joinedGroups;
        int interestedHobbiesCount = user.getHobbies().size();

        Map<String, Object> resp = new HashMap<>();
        resp.put("name", user.getName());
        resp.put("joinedGroups", joinedGroups);
        resp.put("upcomingEvents", upcomingEvents);
        resp.put("newConnections", newConnections);
        resp.put("interestedHobbiesCount", interestedHobbiesCount);
        return resp;
    }

    @GetMapping("/summary")
    public DashboardSummaryDTO getDashboardSummary(Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        User user = userService.getUserById(authUser.getId()).orElseThrow();

        int joinedGroupsCount = user.getGroups().size();
        int upcomingEventsCount = (int) user.getRsvpedEvents().stream()
                .filter(e -> e.getDate().isAfter(OffsetDateTime.now()))
                .count();
        int newConnectionsCount = user.getGroups().stream().mapToInt(g -> g.getMembers().size()).sum() - joinedGroupsCount;
        int interestedHobbiesCount = user.getHobbies().size();

        return new DashboardSummaryDTO(joinedGroupsCount, upcomingEventsCount, newConnectionsCount, interestedHobbiesCount);
    }
}
