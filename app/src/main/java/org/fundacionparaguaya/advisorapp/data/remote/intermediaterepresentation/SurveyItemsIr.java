package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * The intermediate representation of the survey question items from the remote database.
 */

public class SurveyItemsIr {
    @SerializedName("type")
    private String type;
    @SerializedName("enum")
    private List<SurveyItemIr> options;
    @SerializedName("items")
    private List<SurveyItemsIr> items;
}
