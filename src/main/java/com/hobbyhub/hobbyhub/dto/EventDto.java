package com.hobbyhub.hobbyhub.dto;

public class EventDto {
    private Long id;
    private Long groupId;
    private String title;
    private String dateIso;
    private String location;
    private boolean isRsvped;

    public EventDto() {}

    public EventDto(Long id, Long groupId, String title, String dateIso, String location, boolean isRsvped) {
        this.id = id;
        this.groupId = groupId;
        this.title = title;
        this.dateIso = dateIso;
        this.location = location;
        this.isRsvped = isRsvped;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDateIso() { return dateIso; }
    public void setDateIso(String dateIso) { this.dateIso = dateIso; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isRsvped() { return isRsvped; }
    public void setRsvped(boolean rsvped) { isRsvped = rsvped; }
}
