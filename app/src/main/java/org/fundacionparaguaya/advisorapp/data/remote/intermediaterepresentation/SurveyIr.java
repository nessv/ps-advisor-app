package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.ResponseType;
import org.fundacionparaguaya.advisorapp.models.Survey;

import java.util.ArrayList;
import java.util.List;

import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.ECONOMIC;
import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.PERSONAL;

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

    private List<BackgroundQuestion> compilePersonal() {
        return compileBackground(uiSchema.personalQuestions, PERSONAL);
    }

    private List<BackgroundQuestion> compileEconomic() {
        return compileBackground(uiSchema.economicQuestions, ECONOMIC);
    }

    private List<BackgroundQuestion> compileBackground(List<String> names, BackgroundQuestion.QuestionType type) {
        List<BackgroundQuestion> questions = new ArrayList<>();
        for (String name : names) {
            SurveyQuestionIr questionIr = schema.questions.get(name);
            questions.add(new BackgroundQuestion(
                    name,
                    questionIr.title.get("es"),
                    mapResponseType(questionIr.type),
                    type,
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
                return ResponseType.STRING;
            case "number":
                return ResponseType.INTEGER;
            case "integer":
                return ResponseType.INTEGER;
            default:
                throw new IllegalArgumentException("Response type not known!");
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
