package org.fundacionparaguaya.advisorapp.models;

import java.util.List;

/**
 * An Indicator is asked during a survey. Each indicator has a red, yellow, and green level. When the family takes the
 * survey, they will choose one of those levels.
 */

public class Indicator {
    private String name;
    private String dimension;
    private List<IndicatorOption> options;

    public Indicator(String name, String dimension, List<IndicatorOption> options) {
        this.name = name;
        this.dimension = dimension;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public String getDimension() {
        return dimension;
    }

    public List<IndicatorOption> getOptions() {
        return options;
    }
}
