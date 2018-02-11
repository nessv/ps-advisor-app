package org.fundacionparaguaya.advisorapp.fragments;

/**
 * Top most fragment for displaying the life map
 */

public class SurveyLifeMapFragment extends AbstractSurveyFragment {

    //live data for priorities

    public static class PriorityListFragment extends AbstractSurveyFragment
    {
        //on change
        // if no priorities, show turtle image
        //  otherwise on change diff and show priorities.. update header
        //clicking on priority should launch dialog with it prefilled for editing
        //reordering should update order of live data object
    }

    public static class IndicatorLifeMapFragment extends AbstractSurveyFragment
    {
        //recycler view
        //when priorities change
        //so if isSelected, just update number
        //or select/deselect
        //do diff?

        //when indicator is clicked -> show popup dialog
    }
}
