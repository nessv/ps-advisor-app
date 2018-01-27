package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import android.support.annotation.NonNull;

import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.Login;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;
import org.fundacionparaguaya.advisorapp.models.ResponseType;
import org.fundacionparaguaya.advisorapp.models.Survey;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility for mapping IR objects to their corresponding model objects, and vice versa.
 */

public class IrMapper {

    public static Login map(LoginIr ir) {
        return new Login(ir.accessToken, ir.tokenType, ir.expiresIn, ir.refreshToken);
    }

    public static List<Family> mapFamilies(List<FamilyIr> ir) {
        List<Family> families = new ArrayList<>(ir.size());
        for (FamilyIr familyIr : ir) {
            families.add(mapFamily(familyIr));
        }
        return families;
    }

    public static Family mapFamily(FamilyIr ir) {
        return new Family(ir.id, ir.name, null, null, null);
    }

    public static List<Survey> mapSurveys(List<SurveyIr> ir) {
        List<Survey> surveys = new ArrayList<>(ir.size());
        for (SurveyIr surveyIr : ir) {
            surveys.add(mapSurvey(surveyIr));
        }
        return surveys;
    }

    public static Survey mapSurvey(SurveyIr ir) {
        return new Survey(ir.id, mapPersonal(ir), mapEconomic(ir), mapIndicator(ir));
    }

    private static List<PersonalQuestion> mapPersonal(SurveyIr ir) {
        List<PersonalQuestion> questions = new ArrayList<>();
        for (String name : ir.uiSchema.personalQuestions) {
            SurveyQuestionIr questionIr = ir.schema.questions.get(name);
            questions.add(new PersonalQuestion(
                    name,
                    questionIr.title.get("es"),
                    mapResponseType(questionIr.type),
                    questionIr.options));
        }
        return questions;
    }

    private static List<EconomicQuestion> mapEconomic(SurveyIr ir) {
        List<EconomicQuestion> questions = new ArrayList<>();
        for (String name : ir.uiSchema.economicQuestions) {
            SurveyQuestionIr questionIr = ir.schema.questions.get(name);
            questions.add(new EconomicQuestion(
                    name,
                    questionIr.title.get("es"),
                    mapResponseType(questionIr.type),
                    questionIr.options));
        }
        return questions;
    }

    private static List<IndicatorQuestion> mapIndicator(SurveyIr ir) {
        List<IndicatorQuestion> questions = new ArrayList<>();
        for (String name : ir.uiSchema.indicatorQuestions) {
            SurveyQuestionIr questionIr = ir.schema.questions.get(name);

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

    private static ResponseType mapResponseType(String ir) {
        switch (ir) {
            case "string":
                return ResponseType.String;
            case "number":
                return ResponseType.Integer;
            case "integer":
                return ResponseType.Integer;
            default:
                throw new IllegalArgumentException("Response type not known!");
        }
    }

    private static IndicatorOption.Level mapOptionLevel(String ir) {
        switch (ir) {
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
