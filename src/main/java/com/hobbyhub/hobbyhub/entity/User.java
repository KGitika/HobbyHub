package com.hobbyhub.hobbyhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @JsonIgnore
    private String password;

    @ManyToMany
    @JoinTable(name = "user_hobby",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id"))
    private Set<Hobby> hobbies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @JsonIgnoreProperties({"members", "events", "hobby"})
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(mappedBy = "attendees")
    @JsonIgnoreProperties({"group", "attendees"})
    private Set<Event> rsvpedEvents = new HashSet<>();

    // Default constructor
    public User() {}

    // Constructor with fields
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Hobby> getHobbies() { return hobbies; }
    public void setHobbies(Set<Hobby> hobbies) { this.hobbies = hobbies; }

    public Set<Group> getGroups() { return groups; }
    public void setGroups(Set<Group> groups) { this.groups = groups; }

    public Set<Event> getRsvpedEvents() { return rsvpedEvents; }
    public void setRsvpedEvents(Set<Event> rsvpedEvents) { this.rsvpedEvents = rsvpedEvents; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
