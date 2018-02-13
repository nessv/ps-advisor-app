package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.models.FamilyMember;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.Login;
import org.fundacionparaguaya.advisorapp.models.ResponseType;
import org.fundacionparaguaya.advisorapp.models.Snapshot;
import org.fundacionparaguaya.advisorapp.models.Survey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.ECONOMIC;
import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.PERSONAL;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Green;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.None;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Red;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Yellow;

/**
 * A utility for mapping IR objects to their corresponding model objects, and vice versa.
 */

public class IrMapper {

    //region LoginEvent
    public static Login mapLogin(LoginIr ir) {
        return new Login(ir.accessToken, ir.tokenType, ir.expiresIn, ir.refreshToken);
    }
    //endregion LoginEvent

    //region Family
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
                .member(mapFamilyMember(ir.member))
                .build();
    }

    private static FamilyMember mapFamilyMember(FamilyMemberIr ir) {
        if (ir == null) return null;

        return FamilyMember.builder()
                .firstName(ir.firstName)
                .lastName(ir.lastName)
                .birthdate(ir.birthdate)
                .phoneNumber(ir.phoneNumber)
                .identificationType(ir.identificationType)
                .identificationNumber(ir.identificationNumber)
                .gender(ir.gender)
                .profileUrl(ir.profileUrl)
                .build();
    }
    //endregion

    //region Survey
    public static List<Survey> mapSurveys(List<SurveyIr> ir) {
        List<Survey> surveys = new ArrayList<>(ir.size());
        for (SurveyIr surveyIr : ir) {
            surveys.add(mapSurvey(surveyIr));
        }
        return surveys;
    }

    private static Survey mapSurvey(SurveyIr ir) {
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
                return Green;
            case "YELLOW":
                return Yellow;
            case "RED":
                return Red;
            default:
                return None;
        }
    }
    //endregion

    //region Snapshot
    public static List<Snapshot> mapSnapshots(List<SnapshotIr> ir, Family family, Survey survey) {
        List<Snapshot> snapshots = new ArrayList<>(ir.size());
        for (SnapshotIr snapshotIr : ir) {
            snapshots.add(mapSnapshot(snapshotIr, family, survey));
        }
        return snapshots;
    }

    public static Snapshot mapSnapshot(SnapshotIr ir, Family family, Survey survey) {
        return new Snapshot(
                0,
                ir.id,
                family.getId(),
                survey.getId(),
                mapPersonalResponses(ir, survey),
                mapEconomicResponses(ir, survey),
                mapIndicatorResponses(ir, survey),
                mapDate(ir.createdAt));
    }

    public static SnapshotIr mapSnapshot(Snapshot snapshot, Survey survey) {
        SnapshotIr ir = new SnapshotIr();
        ir.id = snapshot.getRemoteId() != null ? snapshot.getRemoteId() : -1;
        ir.surveyId = survey.getRemoteId();
        ir.personalResponses = mapBackgroundResponses(snapshot.getPersonalResponses());
        ir.economicResponses = mapBackgroundResponses(snapshot.getEconomicResponses());
        ir.indicatorResponses = mapIndicatorResponses(snapshot.getIndicatorResponses());
        return ir;
    }

    private static Map<String, Object> mapBackgroundResponses(Map<BackgroundQuestion, String> responses) {
        Map<String, Object> ir = new HashMap<>();
        for (BackgroundQuestion question : responses.keySet()) {
            Object response = responses.get(question);
            if (question.getResponseType() == ResponseType.INTEGER) {
                response = Integer.parseInt((String) response);
            }
            ir.put(question.getName(), response);
        }
        return ir;
    }

    private static Map<String, String> mapIndicatorResponses(Map<IndicatorQuestion, IndicatorOption> responses) {
        Map<String, String> ir = new HashMap<>();
        for (IndicatorQuestion question : responses.keySet()) {
            ir.put(question.getName(), mapIndicatorOptionLevel(responses.get(question).getLevel()));
        }
        return ir;
    }

    private static Map<BackgroundQuestion, String> mapPersonalResponses(SnapshotIr ir, Survey survey) {
        Map<BackgroundQuestion, String> responses = new HashMap<>();
        if (ir.personalResponses == null) return responses;
        for (String question : ir.personalResponses.keySet()) {
            responses.put(getBackgroundQuestion(survey.getPersonalQuestions(), question), mapBackgroundResponseValue(ir.personalResponses.get(question)));
        }
        return responses;
    }

    private static Map<BackgroundQuestion, String> mapEconomicResponses(SnapshotIr ir, Survey survey) {
        Map<BackgroundQuestion, String> responses = new HashMap<>();
        for (String question : ir.economicResponses.keySet()) {
            responses.put(getBackgroundQuestion(survey.getEconomicQuestions(), question), mapBackgroundResponseValue(ir.economicResponses.get(question)));
        }
        return responses;

    }

    private static String mapBackgroundResponseValue(Object ir) {
        if (ir == null) return null;
        return ir.toString();
    }

    private static Map<IndicatorQuestion, IndicatorOption> mapIndicatorResponses(SnapshotIr ir, Survey survey) {
        Map<IndicatorQuestion, IndicatorOption> responses = new HashMap<>();
        for (String question : ir.indicatorResponses.keySet()) {
            IndicatorQuestion indicatorQuestion =
                    getIndicatorQuestion(survey.getIndicatorQuestions(), question);
            responses.put(indicatorQuestion,
                    getIndicatorOption(indicatorQuestion.getOptions(), mapIndicatorOptionLevel(ir.indicatorResponses.get(question))));
        }
        return responses;

    }

    private static BackgroundQuestion getBackgroundQuestion(List<BackgroundQuestion> questions, String name) {
        for (BackgroundQuestion question : questions) {
            if (question.getName().equals(name))
                return question;
        }
        return null;
    }

    private static IndicatorQuestion getIndicatorQuestion(List<IndicatorQuestion> questions, String name) {
        for (IndicatorQuestion question : questions) {
            if (question.getName().equals(name))
                return question;
        }
        throw new UnsupportedOperationException("Could not find a matching indicator question for " + name + "!");
    }

    private static IndicatorOption getIndicatorOption(List<IndicatorOption> indicatorOptions, IndicatorOption.Level level) {
        for (IndicatorOption option : indicatorOptions) {
            if (option.getLevel() == level)
                return option;
        }
        return null;
    }

    private static Date mapDate(String ir) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
        df.setTimeZone(tz);
        try {
            return df.parse(ir);
        } catch (ParseException e) {
            return null;
        }
    }

    private static IndicatorOption.Level mapIndicatorOptionLevel(String level) {
        switch (level.toLowerCase()) {
            case "red":
                return Red;
            case "yellow":
                return Yellow;
            case "green":
                return Green;
            default:
                return None;
        }
    }

    private static String mapIndicatorOptionLevel(IndicatorOption.Level level) {
        switch (level) {
            case Red:
                return "red";
            case Yellow:
                return "yellow";
            case Green:
                return "green";
            default:
                return "";
        }
    }
    //endregion Snapshot
}
