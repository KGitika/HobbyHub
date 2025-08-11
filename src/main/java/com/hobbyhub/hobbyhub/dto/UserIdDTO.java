package com.hobbyhub.hobbyhub.dto;

public class UserIdDTO {
    private Long id;

    public UserIdDTO() {}

    public UserIdDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
