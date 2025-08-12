package com.hobbyhub.hobbyhub.controller;

import com.hobbyhub.hobbyhub.dto.CreateEventRequest;
import com.hobbyhub.hobbyhub.dto.EventDto;
import com.hobbyhub.hobbyhub.dto.RsvpResponse;
import com.hobbyhub.hobbyhub.entity.Event;
import com.hobbyhub.hobbyhub.entity.User;
import com.hobbyhub.hobbyhub.service.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDto> list(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return eventService.getEventsForUser(user).stream()
                .map(e -> toDto(e, user))
                .toList();
    }

    @PostMapping
    public EventDto create(@RequestBody CreateEventRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Event event = eventService.createEvent(user, request.getGroupId(), request.getTitle(),
                OffsetDateTime.parse(request.getDateIso()), request.getLocation());
        return toDto(event, user);
    }

    @PostMapping("/{id}/rsvp")
    public RsvpResponse rsvp(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        eventService.rsvp(user, id);
        return new RsvpResponse(true);
    }

    @DeleteMapping("/{id}/rsvp")
    public RsvpResponse cancel(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        eventService.cancelRsvp(user, id);
        return new RsvpResponse(false);
    }

    private EventDto toDto(Event event, User user) {
        boolean isRsvped = event.getAttendees().contains(user);
        return new EventDto(event.getId(), event.getGroup().getId(), event.getTitle(),
                event.getDate().toString(), event.getLocation(), isRsvped);
    }
}
