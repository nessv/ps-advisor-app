package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * The intermediate representation of the survey UI schema from the remote database.
 */

public class SurveyUiSchemaIr {
    @SerializedName("ui:order")
    private List<String> order;
    @SerializedName("ui:group:personal")
    private List<String> personalQuestions;
    @SerializedName("ui:group:economics")
    private List<String> economicQuestions;
    @SerializedName("ui:group:indicators")
    private List<String> indicatorQuestions;
    @SerializedName("ui:custom:fields")
    private Map<String, SurveyCustomFieldIr> customFields;
}
