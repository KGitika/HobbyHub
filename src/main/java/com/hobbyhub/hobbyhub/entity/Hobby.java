package com.hobbyhub.hobbyhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hobby")
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "hobbies")
    @JsonIgnoreProperties({"hobbies", "groups", "password"})
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "hobby_tag",
        joinColumns = @JoinColumn(name = "hobby_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnoreProperties("hobbies")
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hobby", "members", "events"})
    private Set<Group> groups = new HashSet<>();

    public Hobby() {}

    public Hobby(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Hobby(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }

    public Set<Tag> getTags() { return tags; }
    public void setTags(Set<Tag> tags) { this.tags = tags; }

    public Set<Group> getGroups() { return groups; }
    public void setGroups(Set<Group> groups) { this.groups = groups; }
}
