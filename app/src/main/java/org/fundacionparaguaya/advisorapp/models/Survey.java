package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import org.fundacionparaguaya.advisorapp.data.local.Converters;

import java.util.ArrayList;
import java.util.List;

/**
 * A survey is a set of immutable questions which can be presented to a family in order to
 * collect a snapshot.
 */

@Entity(tableName = "surveys",
        indices={@Index(value="remote_id", unique=true)})
@TypeConverters(Converters.class)
public class Survey {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="remote_id")
    private Long remoteId;
    @ColumnInfo(name="personal_questions")
    private List<BackgroundQuestion> personalQuestions;
    @ColumnInfo(name="economic_questions")
    private List<BackgroundQuestion> economicQuestions;
    @ColumnInfo(name="indicator_questions")
    private List<IndicatorQuestion> indicatorQuestions;

    @Ignore
    public Survey() {
        this(-1, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Ignore
    public Survey(Long remoteId,
                  List<BackgroundQuestion> personalQuestions,
                  List<BackgroundQuestion> economicQuestions,
                  List<IndicatorQuestion> indicatorQuestions) {
        this(-1, remoteId, personalQuestions, economicQuestions, indicatorQuestions);
    }

    public Survey(int id,
                  Long remoteId,
                  List<BackgroundQuestion> personalQuestions,
                  List<BackgroundQuestion> economicQuestions,
                  List<IndicatorQuestion> indicatorQuestions) {
        this.id = id;
        this.remoteId = remoteId;
        this.personalQuestions = personalQuestions;
        this.economicQuestions = economicQuestions;
        this.indicatorQuestions = indicatorQuestions;
    }

    public int getId() {
        return id;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public List<BackgroundQuestion> getPersonalQuestions() {
        return personalQuestions;
    }

    public List<BackgroundQuestion> getEconomicQuestions() {
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
        if (remoteId != null ? !remoteId.equals(survey.remoteId) : survey.remoteId != null)
            return false;
        if (getPersonalQuestions() != null ? !getPersonalQuestions().equals(survey.getPersonalQuestions()) : survey.getPersonalQuestions() != null)
            return false;
        if (getEconomicQuestions() != null ? !getEconomicQuestions().equals(survey.getEconomicQuestions()) : survey.getEconomicQuestions() != null)
            return false;
        return getIndicatorQuestions() != null ? getIndicatorQuestions().equals(survey.getIndicatorQuestions()) : survey.getIndicatorQuestions() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (remoteId != null ? remoteId.hashCode() : 0);
        result = 31 * result + (getPersonalQuestions() != null ? getPersonalQuestions().hashCode() : 0);
        result = 31 * result + (getEconomicQuestions() != null ? getEconomicQuestions().hashCode() : 0);
        result = 31 * result + (getIndicatorQuestions() != null ? getIndicatorQuestions().hashCode() : 0);
        return result;
    }
}
