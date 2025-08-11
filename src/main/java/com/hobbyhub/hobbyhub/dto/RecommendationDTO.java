package com.hobbyhub.hobbyhub.dto;

public class RecommendationDTO {
    private String emoji;
    private String label;
    private int matchPercentage;

    public RecommendationDTO() {}

    public RecommendationDTO(String emoji, String label, int matchPercentage) {
        this.emoji = emoji;
        this.label = label;
        this.matchPercentage = matchPercentage;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }
}
