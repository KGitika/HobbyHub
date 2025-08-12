package com.hobbyhub.hobbyhub.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Temporary controller to satisfy frontend requests for recent activities.
 * TODO: implement actual activity tracking and retrieval.
 */
@RestController
@RequestMapping("/activities")
public class ActivityController {

    @GetMapping("/recent")
    public List<Object> getRecentActivities() {
        // TODO: Implement logic to fetch recent activities
        return Collections.emptyList();
    }
}
