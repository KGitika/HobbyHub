package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.entity.Hobby;
import com.hobbyhub.hobbyhub.service.HobbyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ RestController
@RequestMapping("/hobbies")
public class HobbyController {
    private final HobbyService hobbyService;

    public HobbyController(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    @GetMapping
    public List<Hobby> getAllHobbies() {
        return hobbyService.getAllHobbies();
    }
}
