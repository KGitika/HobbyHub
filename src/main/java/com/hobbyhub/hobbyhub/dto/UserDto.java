package com.hobbyhub.hobbyhub.dto;

import java.util.List;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<String> interests;

    public UserDto() {}

    public UserDto(Long id, String name, String email, List<String> interests) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.interests = interests;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }
}

