package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Tag;
import com.hobbyhub.hobbyhub.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
