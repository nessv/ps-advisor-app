package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import org.fundacionparaguaya.advisorapp.data.local.Converters;

import java.util.HashMap;
import java.util.Map;

import static android.arch.persistence.room.ForeignKey.CASCADE;
import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.ECONOMIC;
import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.PERSONAL;

/**
 * A snapshot represents the family's level of poverty at a specific point in time. It is
 * defined largely by the survey that the family took. The responses are recorded as the family
 * takes the survey and placed in indicatorResponses. Families are able to skip questions, so
 * indicatorResponses might not have a response for every indicator in the survey.
 */

@Entity(tableName = "snapshots",
        indices = {@Index("family_id"), @Index("survey_id")},
        foreignKeys = {
            @ForeignKey(entity = Family.class,
                    parentColumns = "id",
                    childColumns = "family_id",
                    onUpdate = CASCADE,
                    onDelete = CASCADE),
            @ForeignKey(entity = Survey.class,
                    parentColumns = "id",
                    childColumns = "survey_id",
                    onUpdate = CASCADE,
                    onDelete = CASCADE)})
@TypeConverters(Converters.class)
public class Snapshot {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "family_id")
    private int familyId;
    @ColumnInfo(name = "survey_id")
    private int surveyId;
    private Map<BackgroundQuestion, String> personalResponses;
    private Map<BackgroundQuestion, String> economicResponses;
    private Map<IndicatorQuestion, IndicatorOption> indicatorResponses;

    @Ignore
    public Snapshot(Family family, Survey survey) {
        this(0, family.getId(), survey.getId(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public Snapshot(int id, int familyId, int surveyId,
                    Map<BackgroundQuestion, String> personalResponses,
                    Map<BackgroundQuestion, String> economicResponses,
                    Map<IndicatorQuestion, IndicatorOption> indicatorResponses) {
        this.id = id;
        this.familyId = familyId;
        this.surveyId = surveyId;
        this.personalResponses = personalResponses;
        this.economicResponses = economicResponses;
        this.indicatorResponses = indicatorResponses;
    }

    public int getId() {
        return id;
    }

    public int getFamilyId() {
        return familyId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public Map<BackgroundQuestion, String> getPersonalResponses() {
        return personalResponses;
    }

    public Map<BackgroundQuestion, String> getEconomicResponses() {
        return economicResponses;
    }

    public String getBackgroundResponse(BackgroundQuestion question) {
        if (question.getQuestionType() == PERSONAL) {
            return personalResponses.get(question);
        } else if (question.getQuestionType() == ECONOMIC) {
            return economicResponses.get(question);
        } else {
            throw new UnsupportedOperationException(
                    String.format("Unknown question type %s", question.getQuestionType().toString()));
        }
    }

    public Map<IndicatorQuestion, IndicatorOption> getIndicatorResponses() {
        return indicatorResponses;
    }

    public void response(BackgroundQuestion question, String response) {
        personalResponses.put(question, response); // TODO: map to correct list
    }

    public void response(IndicatorQuestion question, IndicatorOption response) {
        indicatorResponses.put(question, response);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snapshot snapshot = (Snapshot) o;

        if (getId() != snapshot.getId()) return false;
        if (getFamilyId() != snapshot.getFamilyId()) return false;
        if (getSurveyId() != snapshot.getSurveyId()) return false;
        if (getPersonalResponses() != null ? !getPersonalResponses().equals(snapshot.getPersonalResponses()) : snapshot.getPersonalResponses() != null)
            return false;
        if (getEconomicResponses() != null ? !getEconomicResponses().equals(snapshot.getEconomicResponses()) : snapshot.getEconomicResponses() != null)
            return false;
        return getIndicatorResponses() != null ? getIndicatorResponses().equals(snapshot.getIndicatorResponses()) : snapshot.getIndicatorResponses() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getFamilyId();
        result = 31 * result + getSurveyId();
        result = 31 * result + (getPersonalResponses() != null ? getPersonalResponses().hashCode() : 0);
        result = 31 * result + (getEconomicResponses() != null ? getEconomicResponses().hashCode() : 0);
        result = 31 * result + (getIndicatorResponses() != null ? getIndicatorResponses().hashCode() : 0);
        return result;
    }
}
