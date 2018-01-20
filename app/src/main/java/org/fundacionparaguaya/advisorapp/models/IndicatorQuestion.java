package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.Entity;

import java.util.List;

/**
 * A question targeting an indicator which can be presented to a family and responded to from a
 * survey.
 */

@Entity
public class IndicatorQuestion extends SurveyQuestion {
    private Indicator indicator;
    private List<IndicatorOption> options;

    public IndicatorQuestion(Indicator indicator) {
        super(indicator.getName(), indicator.getDimension());
        this.indicator = indicator;
        this.options = indicator.getOptions();
    }

    public Indicator getIndicator() {
        return indicator;
    }
}
