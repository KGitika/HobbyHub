package com.hobbyhub.hobbyhub.dto;

public class RsvpResponse {
    private boolean isRsvped;

    public RsvpResponse(boolean isRsvped) {
        this.isRsvped = isRsvped;
    }

    public boolean isRsvped() { return isRsvped; }
    public void setRsvped(boolean rsvped) { isRsvped = rsvped; }
}
