package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import org.fundacionparaguaya.advisorapp.data.local.Converters;

import java.util.ArrayList;
import java.util.List;

/**
 * A survey is a set of immutable questions which can be presented to a family in order to
 * collect a snapshot.
 */

@Entity(tableName = "surveys")
@TypeConverters(Converters.class)
public class Survey {
    @PrimaryKey
    private int id;
    @Ignore
    private List<SurveyQuestion> personalQuestions;
    @Ignore
    private List<SurveyQuestion> economicQuestions;
    @ColumnInfo(name="indicator_questions")
    private List<IndicatorQuestion> indicatorQuestions;

    public Survey(int id, List<IndicatorQuestion> indicatorQuestions) {
        this(id, new ArrayList<>(), new ArrayList<>(), indicatorQuestions);
    }

    @Ignore
    public Survey(int id, List<SurveyQuestion> personalQuestions, List<SurveyQuestion> economicQuestions, List<IndicatorQuestion> indicatorQuestions) {
        this.id = id;
        this.personalQuestions = personalQuestions;
        this.economicQuestions = economicQuestions;
        this.indicatorQuestions = indicatorQuestions;
    }

    public int getId() {
        return id;
    }

    public List<SurveyQuestion> getPersonalQuestions() {
        return personalQuestions;
    }

    public List<SurveyQuestion> getEconomicQuestions() {
        return economicQuestions;
    }

    public List<IndicatorQuestion> getIndicatorQuestions() {
        return indicatorQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Survey survey = (Survey) o;

        if (getId() != survey.getId()) return false;
        if (getPersonalQuestions() != null ? !getPersonalQuestions().equals(survey.getPersonalQuestions()) : survey.getPersonalQuestions() != null)
            return false;
        if (getEconomicQuestions() != null ? !getEconomicQuestions().equals(survey.getEconomicQuestions()) : survey.getEconomicQuestions() != null)
            return false;
        return getIndicatorQuestions() != null ? getIndicatorQuestions().equals(survey.getIndicatorQuestions()) : survey.getIndicatorQuestions() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getPersonalQuestions() != null ? getPersonalQuestions().hashCode() : 0);
        result = 31 * result + (getEconomicQuestions() != null ? getEconomicQuestions().hashCode() : 0);
        result = 31 * result + (getIndicatorQuestions() != null ? getIndicatorQuestions().hashCode() : 0);
        return result;
    }
}
