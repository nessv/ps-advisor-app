package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.IndicatorAdapter;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.viewcomponents.IndicatorCard;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import static org.fundacionparaguaya.advisorapp.fragments.ChooseIndicatorFragment.SelectedIndicator.GREEN;
import static org.fundacionparaguaya.advisorapp.fragments.ChooseIndicatorFragment.SelectedIndicator.NONE;
import static org.fundacionparaguaya.advisorapp.fragments.ChooseIndicatorFragment.SelectedIndicator.RED;
import static org.fundacionparaguaya.advisorapp.fragments.ChooseIndicatorFragment.SelectedIndicator.YELLOW;

/**
 *
 */

public class ChooseIndicatorFragment extends AbstractSurveyFragment {

    IndicatorCard mGreenIndicator;
    IndicatorCard mYellowIndicator;
    IndicatorCard mRedIndicator;

    IndicatorQuestion question;

    SurveyIndicatorsFragment parentFragment;

    @Nullable
    InjectionViewModelFactory mViewModelFactory;
    SharedSurveyViewModel mSurveyViewModel;
    IndicatorAdapter adapter;

    String greenImage;    String greenText;
    String yellowImage;   String yellowText;
    String redImage;      String redText;

    enum SelectedIndicator { RED, YELLOW, GREEN, NONE};

    SelectedIndicator selectedIndicator = NONE;

    public ChooseIndicatorFragment newInstance(IndicatorAdapter adapter, IndicatorQuestion question,
                                               String greenImage, String greenText,
                                               String yellowImage, String yellowText,
                                               String redImage, String redText
    ){
        ChooseIndicatorFragment fragment = new ChooseIndicatorFragment();

        this.adapter = adapter;
        this.question = question;
        this.greenImage = greenImage; this.greenText = greenText;
        this.yellowImage = yellowImage; this.yellowText = yellowText;
        this.redImage = redImage; this.redText = redText;

        parentFragment = (SurveyIndicatorsFragment) adapter.returnParent();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_chooseindicator, container, false);

        mGreenIndicator = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_green);
        mYellowIndicator = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_yellow);
        mRedIndicator = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_red);

        //Set Green Indicator
        this.mGreenIndicator.setImage(Uri.parse(greenImage));
        this.mGreenIndicator.setText(greenText);
        mGreenIndicator.setColor(IndicatorCard.CardColor.GREEN);

        //Set Yellow Indicator
        this.mYellowIndicator.setImage(Uri.parse(yellowImage));
        this.mYellowIndicator.setText(yellowText);
        this.mYellowIndicator.setColor(IndicatorCard.CardColor.YELLOW);

        //Set Red Indicator
        this.mRedIndicator.setImage(Uri.parse(redImage));
        this.mRedIndicator.setText(redText);
        this.mRedIndicator.setColor(IndicatorCard.CardColor.RED);

        mGreenIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndicator != GREEN){
                    mGreenIndicator.setSelected(true);
                    mYellowIndicator.setSelected(false);
                    mRedIndicator.setSelected(false);
                    mSurveyViewModel.addIndicatorResponse(question, question.getOptions().get(0));
                    selectedIndicator = GREEN;
                    updateParent();
                } else {
                    mGreenIndicator.setSelected(false);
                    selectedIndicator = NONE;
                    mSurveyViewModel.addSkippedIndicator(question);
                }
            }
        });

        mYellowIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndicator != YELLOW){
                    mGreenIndicator.setSelected(false);
                    mYellowIndicator.setSelected(true);
                    mRedIndicator.setSelected(false);
                    mSurveyViewModel.addIndicatorResponse(question, question.getOptions().get(1));
                    selectedIndicator = YELLOW;
                    updateParent();
                } else {
                    mYellowIndicator.setSelected(false);
                    selectedIndicator = NONE;
                    mSurveyViewModel.addSkippedIndicator(question);
                }
            }
        });

        mRedIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndicator != RED){
                    mGreenIndicator.setSelected(false);
                    mYellowIndicator.setSelected(false);
                    mRedIndicator.setSelected(true);
                    mSurveyViewModel.addIndicatorResponse(question, question.getOptions().get(2));
                    selectedIndicator = RED;
                    updateParent();
                } else {
                    mRedIndicator.setSelected(false);
                    selectedIndicator = NONE;
                    mSurveyViewModel.addSkippedIndicator(question);
                }
            }
        });
        return rootView;


    }

    private void updateParent(){

        if (parentFragment !=null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    parentFragment.nextQuestion();
                }
            }, 500); // Millisecond 1000 = 1 sec
        }
    }

    public SelectedIndicator getSelectedIndicator(){
        return selectedIndicator;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSurveyViewModel = ViewModelProviders
                .of(getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

    }
}
