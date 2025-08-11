package com.hobbyhub.hobbyhub.dto;

public class DashboardSummaryDTO {
    private int joinedGroupsCount;
    private int upcomingEventsCount;
    private int newConnectionsCount;
    private int interestedHobbiesCount;

    public DashboardSummaryDTO() {
    }

    public DashboardSummaryDTO(int joinedGroupsCount, int upcomingEventsCount, int newConnectionsCount, int interestedHobbiesCount) {
        this.joinedGroupsCount = joinedGroupsCount;
        this.upcomingEventsCount = upcomingEventsCount;
        this.newConnectionsCount = newConnectionsCount;
        this.interestedHobbiesCount = interestedHobbiesCount;
    }

    public int getJoinedGroupsCount() {
        return joinedGroupsCount;
    }

    public void setJoinedGroupsCount(int joinedGroupsCount) {
        this.joinedGroupsCount = joinedGroupsCount;
    }

    public int getUpcomingEventsCount() {
        return upcomingEventsCount;
    }

    public void setUpcomingEventsCount(int upcomingEventsCount) {
        this.upcomingEventsCount = upcomingEventsCount;
    }

    public int getNewConnectionsCount() {
        return newConnectionsCount;
    }

    public void setNewConnectionsCount(int newConnectionsCount) {
        this.newConnectionsCount = newConnectionsCount;
    }

    public int getInterestedHobbiesCount() {
        return interestedHobbiesCount;
    }

    public void setInterestedHobbiesCount(int interestedHobbiesCount) {
        this.interestedHobbiesCount = interestedHobbiesCount;
    }
}
