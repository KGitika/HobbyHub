package com.hobbyhub.hobbyhub.service;

import com.hobbyhub.hobbyhub.entity.Event;
import com.hobbyhub.hobbyhub.entity.Group;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.repository.EventRepository;
import com.hobbyhub.hobbyhub.repository.GroupRepository;
import com.hobbyhub.hobbyhub.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<Event> getEventsForGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        return eventRepository.findByGroup(group);
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsForUser(User user) {
        User managed = userRepository.findById(user.getId()).orElseThrow();
        return eventRepository.findByGroupIn(managed.getGroups());
    }

    @Transactional
    public Event createEvent(User user, Long groupId, String title, OffsetDateTime date, String location) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        Set<Long> groupMembers = group.getMembers().stream().map(User::getId).collect(Collectors.toSet());
        if (!groupMembers.contains(user.getId())) {
            throw new RuntimeException("User is not a member of this group");
        }
        User managedUser = userRepository.findById(user.getId()).orElseThrow();
        Event event = new Event();
        event.setTitle(title);
        event.setDate(date);
        event.setLocation(location);
        event.setGroup(group);
        event.getAttendees().add(managedUser);
        managedUser.getRsvpedEvents().add(event);
        return eventRepository.save(event);
    }

    @Transactional
    public void rsvp(User user, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (!event.getGroup().getMembers().contains(user)) {
            throw new RuntimeException("User is not a member of this group");
        }
        User managedUser = userRepository.findById(user.getId()).orElseThrow();
        event.getAttendees().add(managedUser);
        managedUser.getRsvpedEvents().add(event);
        eventRepository.save(event);
    }

    @Transactional
    public void cancelRsvp(User user, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        User managedUser = userRepository.findById(user.getId()).orElseThrow();
        event.getAttendees().remove(managedUser);
        managedUser.getRsvpedEvents().remove(event);
        eventRepository.save(event);
    }
}
