package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Event;
import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.repository.EventRepository;
import com.hobbyhub.hobbyhub.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final GroupRepository groupRepository;

    public EventService(EventRepository eventRepository, GroupRepository groupRepository) {
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
    }

    public List<Event> getEventsForGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        return eventRepository.findByGroup(group);
    }
}
