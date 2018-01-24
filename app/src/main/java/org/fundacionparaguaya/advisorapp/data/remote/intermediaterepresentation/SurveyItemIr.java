package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

/**
 * The intermediate representation of the survey question item from the remote database.
 */

public class SurveyItemIr {
    @SerializedName("url")
    private String url;
    @SerializedName("value")
    private String value;
    @SerializedName("description")
    private String description;
}
