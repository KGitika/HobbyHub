package com.hobbyhub.hobbyhub.dto;

public class CreateEventRequest {
    private Long groupId;
    private String title;
    private String dateIso;
    private String location;

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDateIso() { return dateIso; }
    public void setDateIso(String dateIso) { this.dateIso = dateIso; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
