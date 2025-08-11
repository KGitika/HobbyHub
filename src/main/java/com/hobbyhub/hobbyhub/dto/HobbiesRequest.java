package com.hobbyhub.hobbyhub.dto;

import java.util.List;

/**
 * Request payload for updating a user's hobbies.
 * Uses hobby names instead of numeric IDs to align with API contract.
 */
public class HobbiesRequest {
    private List<String> hobbies;

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}

