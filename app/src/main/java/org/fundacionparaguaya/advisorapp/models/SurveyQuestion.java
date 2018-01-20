package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.ColumnInfo;

/**
 * A question which can be presented to a family and responded to from a survey.
 */

public class SurveyQuestion {
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="description")
    private String description;

    public SurveyQuestion(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
