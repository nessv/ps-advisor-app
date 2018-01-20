package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.Map;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * A snapshot represents the family's level of poverty at a specific point in time. It is
 * defined largely by the survey that the family took. The responses are recorded as the family
 * takes the survey and placed in indicatorResponses. Families are able to skip questions, so
 * indicatorResponses might not have a response for every indicator in the survey.
 */

@Entity(tableName = "snapshots",
        indices = @Index("family_id") ,
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
public class Snapshot {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "family_id")
    private int familyId;
    @ColumnInfo(name = "survey_id")
    private int surveyId;
    private Map<SurveyQuestion, String> personalResponses;
    private Map<SurveyQuestion, String> economicResponses;
    private Map<IndicatorQuestion, String> indicatorResponses;
    private Date date;

    public Snapshot(int id, int familyId, int surveyId,
                    Map<SurveyQuestion, String> personalResponses,
                    Map<SurveyQuestion, String> economicResponses,
                    Map<IndicatorQuestion, String> indicatorResponses,
                    Date date) {
        this.id = id;
        this.familyId = familyId;
        this.surveyId = surveyId;
        this.personalResponses = personalResponses;
        this.economicResponses = economicResponses;
        this.indicatorResponses = indicatorResponses;
        this.date = date;
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

    public Map<SurveyQuestion, String> getPersonalResponses() {
        return personalResponses;
    }

    public Map<SurveyQuestion, String> getEconomicResponses() {
        return economicResponses;
    }

    public Map<IndicatorQuestion, String> getIndicatorResponses() {
        return indicatorResponses;
    }

    public Date getDate() {
        return date;
    }
}
