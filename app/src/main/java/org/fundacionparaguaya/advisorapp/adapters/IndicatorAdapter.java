package org.fundacionparaguaya.advisorapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.fundacionparaguaya.advisorapp.fragments.ChooseIndicatorFragment;
import org.fundacionparaguaya.advisorapp.fragments.SurveyIndicatorsFragment;
import org.fundacionparaguaya.advisorapp.fragments.SurveySummaryFragment;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Adapter class for the indicators
 */

public class IndicatorAdapter extends FragmentStatePagerAdapter {

    private List<IndicatorQuestion> indicatorQuestionList;

    private Set<IndicatorQuestion> skippedQuestions;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    SharedSurveyViewModel mSurveyViewModel;

    SurveyIndicatorsFragment mSurveyFragment;

    public IndicatorAdapter(FragmentManager fragmentManager, SharedSurveyViewModel surveyViewModel, SurveyIndicatorsFragment parentFrag) {
        super(fragmentManager);

        mSurveyViewModel = surveyViewModel;
        mSurveyFragment = parentFrag;

        indicatorQuestionList = mSurveyViewModel.getSurveyInProgress().getIndicatorQuestions();
        loadFragments();
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public SurveyIndicatorsFragment returnParent(){
        return mSurveyFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public IndicatorQuestion getQuestion(int position){
        return indicatorQuestionList.get(position);
    }

    /**
     * Function loads fragments into the arraylist above
     * - Set picture and text for each indicator here
     * - Set up fragment here
     */
    private void loadFragments() {
        ChooseIndicatorFragment tempFrag;
        String greenImage;    String greenText;
        String yellowImage;   String yellowText;
        String redImage;      String redText;
        for(int counter = 0; counter < indicatorQuestionList.size(); counter++){
            tempFrag = new ChooseIndicatorFragment();

            greenImage = indicatorQuestionList.get(counter).getIndicator().getOptions().get(0).getImageUrl();
            yellowImage = indicatorQuestionList.get(counter).getIndicator().getOptions().get(1).getImageUrl();
            redImage = indicatorQuestionList.get(counter).getIndicator().getOptions().get(2).getImageUrl();

            greenText = indicatorQuestionList.get(counter).getIndicator().getOptions().get(0).getDescription();
            yellowText = indicatorQuestionList.get(counter).getIndicator().getOptions().get(1).getDescription();
            redText = indicatorQuestionList.get(counter).getIndicator().getOptions().get(2).getDescription();

           tempFrag.newInstance(this, indicatorQuestionList.get(counter),
                                greenImage,    greenText,
                                yellowImage,   yellowText,
                                redImage,      redText);

           fragmentList.add(counter, tempFrag);
        }
        SurveySummaryFragment summaryFragment = new SurveySummaryFragment();
        summaryFragment.newInstance(mSurveyViewModel);
        fragmentList.add(summaryFragment);

    }

}