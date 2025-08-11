package com.hobbyhub.hobbyhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hobby_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"groups", "hobbies", "rsvpedEvents", "password"})
    private User owner;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnoreProperties({"groups", "hobbies", "rsvpedEvents", "password"})
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"group", "attendees"})
    private Set<Event> events = new HashSet<>();

    public Group() {}

    public Group(String title, String description, Hobby hobby) {
        this.title = title;
        this.description = description;
        this.hobby = hobby;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Hobby getHobby() { return hobby; }
    public void setHobby(Hobby hobby) { this.hobby = hobby; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Set<User> getMembers() { return members; }
    public void setMembers(Set<User> members) { this.members = members; }

    public Set<Event> getEvents() { return events; }
    public void setEvents(Set<Event> events) { this.events = events; }
}
