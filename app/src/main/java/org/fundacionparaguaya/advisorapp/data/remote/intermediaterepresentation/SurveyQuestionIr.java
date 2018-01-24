package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * The intermediate representation of the survey question from the remote database.
 */

public class SurveyQuestionIr {
    @SerializedName("type")
    private String type;
    @SerializedName("title")
    private Map<String, String> title;
    @SerializedName("items")
    private List<SurveyItemsIr> items;
    @SerializedName("enum")
    private List<String> options;
    @SerializedName("enumNames")
    private List<String> optionNames;
}
