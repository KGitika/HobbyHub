package com.hobbyhub.hobbyhub.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private OffsetDateTime date;
    private String location;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToMany
    @JoinTable(name = "event_rsvp",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<User> attendees = new HashSet<>();

    public Event() {}

    public Event(String title, String description, OffsetDateTime date, String location, Group group) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.group = group;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public OffsetDateTime getDate() { return date; }
    public void setDate(OffsetDateTime date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }

    public Set<User> getAttendees() { return attendees; }
    public void setAttendees(Set<User> attendees) { this.attendees = attendees; }
}
