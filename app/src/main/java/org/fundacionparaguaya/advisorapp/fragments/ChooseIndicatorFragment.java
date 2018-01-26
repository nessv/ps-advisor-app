package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yarolegovich.discretescrollview.transform.Pivot;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.IndicatorAdapter;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
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
        mGreenIndicator.setImage(Uri.parse(greenImage));
        mGreenIndicator.setText(greenText);

        //Set Yellow Indicator
        mYellowIndicator.setImage(Uri.parse(yellowImage));
        mYellowIndicator.setText(yellowText);

        //Set Red Indicator
        mRedIndicator.setImage(Uri.parse(redImage));
        mRedIndicator.setText(redText);

        IndicatorOption test = mSurveyViewModel.getResponseForIndicator(question);
        IndicatorOption test1 = question.getOptions().get(0);

        try {
            if (test.equals(question.getOptions().get(0))) {
                mGreenIndicator.setSelected(true);
            } else if(test.equals(question.getOptions().get(1))) {
                mYellowIndicator.setSelected(true);
            } else if (test.equals(question.getOptions().get(2))) {
                mRedIndicator.setSelected(true);
            }
        } catch (NullPointerException e){
            selectedIndicator = NONE;
            Toast.makeText(getContext(), "Null Pointer", Toast.LENGTH_SHORT).show();
        }


        mGreenIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndicator != GREEN){
                    setSelected(GREEN);
                } else {
                    setSelected(NONE);
                }
            }
        });

        mYellowIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndicator != YELLOW){
                    setSelected(YELLOW);
                } else {
                    setSelected(NONE);
                }
            }
        });

        mRedIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndicator != RED){
                    setSelected(RED);
                } else {
                    setSelected(NONE);
                }
            }
        });
        return rootView;


    }

    /**
     * Sets the desired selected indicator
     * @param indicator
     */

    private void setSelected(SelectedIndicator indicator){
        switch(indicator){
            case GREEN:
                mGreenIndicator.setSelected(true);
                mYellowIndicator.setSelected(false);
                mRedIndicator.setSelected(false);
                mSurveyViewModel.addIndicatorResponse(question, question.getOptions().get(0));
                selectedIndicator = GREEN;
                break;
            case YELLOW:
                mGreenIndicator.setSelected(false);
                mYellowIndicator.setSelected(true);
                mRedIndicator.setSelected(false);
                mSurveyViewModel.addIndicatorResponse(question, question.getOptions().get(1));
                selectedIndicator = YELLOW;
                break;
            case RED:
                mGreenIndicator.setSelected(false);
                mYellowIndicator.setSelected(false);
                mRedIndicator.setSelected(true);
                mSurveyViewModel.addIndicatorResponse(question, question.getOptions().get(2));
                selectedIndicator = RED;
                break;
            case NONE:
                mGreenIndicator.setSelected(false);
                mYellowIndicator.setSelected(false);
                mRedIndicator.setSelected(false);
                mSurveyViewModel.addSkippedIndicator(question);
                selectedIndicator = NONE;
                break;
            default:
                mGreenIndicator.setSelected(false);
                mYellowIndicator.setSelected(false);
                mRedIndicator.setSelected(false);
                mSurveyViewModel.addSkippedIndicator(question);
                selectedIndicator = NONE;
                break;
        }
        updateParent();
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
