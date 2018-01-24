package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;
import org.fundacionparaguaya.advisorapp.models.ResponseType;
import org.fundacionparaguaya.advisorapp.models.Survey;

import java.util.ArrayList;
import java.util.List;

/**
 * The intermediate representation of the survey from the remote database.
 */

public class SurveyIr {
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("survey_schema")
    SurveySchemaIr schema;
    @SerializedName("survey_ui_schema")
    SurveyUiSchemaIr uiSchema;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("last_modified_at")
    String lastModifiedAt;
    @SerializedName("user_created")
    UserIr author;

    public Survey compile() {
        return new Survey(id, compilePersonal(), compileEconomic(), compileIndicator());
    }

    private List<PersonalQuestion> compilePersonal() {
        List<PersonalQuestion> questions = new ArrayList<>();
        for (String name : uiSchema.personalQuestions) {
            SurveyQuestionIr questionIr = schema.questions.get(name);
            questions.add(new PersonalQuestion(
                    name,
                    questionIr.title.get("es"),
                    mapResponseType(questionIr.type),
                    questionIr.options));
        }
        return questions;
    }

    private List<EconomicQuestion> compileEconomic() {
        List<EconomicQuestion> questions = new ArrayList<>();
        for (String name : uiSchema.economicQuestions) {
            SurveyQuestionIr questionIr = schema.questions.get(name);
            questions.add(new EconomicQuestion(
                    name,
                    questionIr.title.get("es"),
                    mapResponseType(questionIr.type),
                    questionIr.options));
        }
        return questions;
    }

    private List<IndicatorQuestion> compileIndicator() {
        List<IndicatorQuestion> questions = new ArrayList<>();
        for (String name : uiSchema.indicatorQuestions) {
            SurveyQuestionIr questionIr = schema.questions.get(name);

            Indicator indicator = new Indicator(name, questionIr.title.get("es"));
            List<IndicatorOption> options = new ArrayList<>();
            for (IndicatorOptionIr optionIr : questionIr.indicatorOptions.values) {
                options.add(new IndicatorOption(
                        optionIr.description,
                        optionIr.url,
                        mapOptionLevel(optionIr.value)
                ));
            }
            indicator.setOptions(options);

            questions.add(new IndicatorQuestion(indicator));
        }
        return questions;
    }

    private ResponseType mapResponseType(String from) {
        switch (from) {
            case "string":
                return ResponseType.String;
            case "number":
                return ResponseType.Integer;
            case "integer":
                return ResponseType.Integer;
            default:
                throw new IllegalArgumentException("Response type not know!");
        }
    }

    private IndicatorOption.Level mapOptionLevel(String from) {
        switch (from) {
            case "GREEN":
                return IndicatorOption.Level.Green;
            case "YELLOW":
                return IndicatorOption.Level.Yellow;
            case "RED":
                return IndicatorOption.Level.Red;
            default:
                return IndicatorOption.Level.None;
        }
    }
}
