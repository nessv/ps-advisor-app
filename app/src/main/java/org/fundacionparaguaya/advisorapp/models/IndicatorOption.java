package org.fundacionparaguaya.advisorapp.models;

import com.google.gson.annotations.SerializedName;

/**
 *  An indicator option is a definition for one of the three levels an indicator can have. It's level is determined
 *  by the parent indicator
 */

public class IndicatorOption {
    public enum Level {Red, Yellow, Green, None}

    @SerializedName("description")
    private String description;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("level")
    private Level level;

    public IndicatorOption(String description, String imageUrl, Level level) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Level getLevel() {
        return level;
    }
}