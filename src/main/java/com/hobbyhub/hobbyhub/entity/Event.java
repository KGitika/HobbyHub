package com.hobbyhub.hobbyhub.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate date;
    private String location;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Event() {}

    public Event(String title, String description, LocalDate date, String location, Group group) {
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

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
}