package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.Login;
import org.fundacionparaguaya.advisorapp.models.ResponseType;
import org.fundacionparaguaya.advisorapp.models.Survey;

import java.util.ArrayList;
import java.util.List;

import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.ECONOMIC;
import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.PERSONAL;

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

    public static FamilyIr mapFamily(Family family) {
        FamilyIr ir = new FamilyIr();
        ir.id = family.getRemoteId() != null ? family.getRemoteId() : -1;
        ir.name = family.getName();
        ir.code = family.getCode();
        ir.active = family.isActive();
        return ir;
    }

    public static Family mapFamily(FamilyIr ir) {
        return Family.builder()
                .remoteId(ir.id)
                .name(ir.name)
                .code(ir.code)
                .build();
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

    private static List<BackgroundQuestion> mapPersonal(SurveyIr ir) {
        return mapBackground(PERSONAL, ir);
    }

    private static List<BackgroundQuestion> mapEconomic(SurveyIr ir) {
        return mapBackground(ECONOMIC, ir);
    }

    private static List<BackgroundQuestion> mapBackground(
            BackgroundQuestion.QuestionType type, SurveyIr ir) {

        List<String> names = type == PERSONAL ?
                ir.uiSchema.personalQuestions : ir.uiSchema.economicQuestions;
        List<BackgroundQuestion> questions = new ArrayList<>();
        for (String name : names) {
            SurveyQuestionIr questionIr = ir.schema.questions.get(name);
            questions.add(new BackgroundQuestion(
                    name,
                    questionIr.title.get("es"),
                    mapResponseType(questionIr.type),
                    type,
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
                return ResponseType.STRING;
            case "number":
                return ResponseType.INTEGER;
            case "integer":
                return ResponseType.INTEGER;
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
