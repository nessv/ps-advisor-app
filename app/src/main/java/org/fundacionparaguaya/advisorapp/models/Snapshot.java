package org.fundacionparaguaya.advisorapp.models;

import java.util.Date;
import java.util.HashMap;

/**
 * A snapshot represents the family's level of poverty at a specific point in time. It is defined largely
 * by the survey that the family took. The responses are recorded as the family takes the survey and placed in
 * indicatorResponses. Families are able to skip questions, so indicatorResponses might not have a response
 * for every indicator in the survey.
 */

public class Snapshot
{
    private Family family;
    private Survey survey;
    private HashMap<Indicator, IndicatorOption> indicatorResponses;
    private HashMap<EconomicQuestion, String> economicResponses;
    private Date date;

    public Snapshot(Survey survey, Family family)
    {
        this.survey = survey;
        this.family = family;
    }

    public IndicatorOption getResponseForIndicator(Indicator i)
    {
        return indicatorResponses.get(i);
    }

    public IndicatorOption.Level getLevelForIndicator(Indicator i)
    {
        return i.getLevelForOption(indicatorResponses.get(i));
    }
}
