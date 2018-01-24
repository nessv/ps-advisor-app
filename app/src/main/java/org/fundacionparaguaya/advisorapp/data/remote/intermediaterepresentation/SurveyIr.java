package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

/**
 * The intermediate representation of the survey from the remote database.
 */

public class SurveyIr {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("survey_schema")
    private SurveySchemaIr schema;
    @SerializedName("survey_ui_schema")
    private SurveyUiSchemaIr uiSchema;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("last_modified_at")
    private String lastModifiedAt;
    @SerializedName("user_created")
    private UserIr author;
}
