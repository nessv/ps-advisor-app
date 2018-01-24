package org.fundacionparaguaya.advisorapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.fundacionparaguaya.advisorapp.fragments.IndicatorFragment;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for the indicators
 */

public class IndicatorAdapter extends FragmentPagerAdapter {

    private List<IndicatorQuestion> indicatorQuestionList;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    SharedSurveyViewModel mSurveyViewModel;

    public IndicatorAdapter(FragmentManager fragmentManager, SharedSurveyViewModel surveyViewModel) {
        super(fragmentManager);

        mSurveyViewModel = surveyViewModel;

        indicatorQuestionList = mSurveyViewModel.getSurveyInProgress().getIndicatorQuestions();
        loadFragments();
    }

    //TODO implement
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    /**
     * Function loads fragments into the arraylist above
     * - Set picture and text for each indicator here
     * - Set up fragment here
     */
    private void loadFragments() {
        IndicatorFragment tempFrag = new IndicatorFragment();
        String greenImage;    String greenText;
        String yellowImage;   String yellowText;
        String redImage;      String redText;
        for(int counter = 0; counter < indicatorQuestionList.size(); counter++){
            greenImage = indicatorQuestionList.get(counter).getIndicator().getOptions().get(0).getImageUrl();
            yellowImage = indicatorQuestionList.get(counter).getIndicator().getOptions().get(1).getImageUrl();
            redImage = indicatorQuestionList.get(counter).getIndicator().getOptions().get(2).getImageUrl();

            greenText = indicatorQuestionList.get(counter).getIndicator().getOptions().get(0).getDescription();
            yellowText = indicatorQuestionList.get(counter).getIndicator().getOptions().get(1).getDescription();
            redText = indicatorQuestionList.get(counter).getIndicator().getOptions().get(2).getDescription();

           tempFrag.newInstance(greenImage,    greenText,
                                yellowImage,   yellowText,
                                redImage,      redText);
        }
    }

}