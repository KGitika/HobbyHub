package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Event;
import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.repository.EventRepository;
import com.hobbyhub.hobbyhub.repository.GroupRepository;
import com.hobbyhub.hobbyhub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_addsCreatorAsAttendee() {
        User user = new User();
        user.setId(1L);

        Group group = new Group();
        group.setId(2L);
        group.getMembers().add(user);

        when(groupRepository.findById(2L)).thenReturn(Optional.of(group));
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));

        Event event = eventService.createEvent(user, 2L, "Party", OffsetDateTime.now(), "Location");

        assertTrue(event.getAttendees().contains(user));
        verify(eventRepository).save(any(Event.class));
    }
}
