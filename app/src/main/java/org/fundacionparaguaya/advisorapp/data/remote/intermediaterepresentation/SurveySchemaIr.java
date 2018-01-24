package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * The intermediate representation of the survey schema from the remote database.
 */

public class SurveySchemaIr {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("required")
    private List<String> requiredQuestions;
    @SerializedName("properties")
    private Map<String, SurveyQuestionIr> questions;
}
