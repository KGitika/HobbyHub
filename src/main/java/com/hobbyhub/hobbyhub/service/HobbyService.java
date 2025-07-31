package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Hobby;
import com.hobbyhub.hobbyhub.repository.HobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HobbyService {
    private final HobbyRepository hobbyRepository;

    public HobbyService(HobbyRepository hobbyRepository) {
        this.hobbyRepository = hobbyRepository;
    }

    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }

    public Optional<Hobby> getHobbyById(Long id) {
        return hobbyRepository.findById(id);
    }

    public Hobby save(Hobby hobby) {
        return hobbyRepository.save(hobby);
    }
}
