package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.dto.RecommendationDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @GetMapping("/groups")
    public List<RecommendationDTO> getGroupRecommendations() {
        // TODO: implement group recommendation logic
        return Collections.emptyList();
    }
}
