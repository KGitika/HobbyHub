package com.hobbyhub.hobbyhub.dto;

public class MembershipResponse {
    private boolean isMember;

    public MembershipResponse(boolean isMember) {
        this.isMember = isMember;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }
}
