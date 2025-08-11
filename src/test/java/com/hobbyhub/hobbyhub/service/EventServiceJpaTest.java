package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Event;
import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.repository.EventRepository;
import com.hobbyhub.hobbyhub.repository.GroupRepository;
import com.hobbyhub.hobbyhub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(EventService.class)
class EventServiceJpaTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private EventRepository eventRepository;

    @Test
    void createEventPersistsCreatorRsvp() {
        User user = new User("Alice", "alice@example.com", "pwd");
        userRepository.save(user);

        Group group = new Group();
        group.setTitle("Chess Club");
        group.setDescription("desc");
        group.setOwner(user);
        group.getMembers().add(user);
        groupRepository.save(group);

        Event event = eventService.createEvent(user, group.getId(), "Session", OffsetDateTime.now(), "online");

        Event reloaded = eventRepository.findById(event.getId()).orElseThrow();
        assertTrue(reloaded.getAttendees().contains(userRepository.findById(user.getId()).orElseThrow()));
    }
}

