package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.Survey;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel.*;

import javax.inject.Inject;

/**
 * Intro page on a new survey
 */

public class SurveyIntroFragment extends AbstractSurveyFragment
{
    static String FRAGMENT_TAG = "SurveyIntroFragment";

    @Inject
    InjectionViewModelFactory mViewModelFactory;

    SharedSurveyViewModel mSurveyViewModel;

    //need the family name

    public SurveyIntroFragment()
    {
        setShowFooter(false);
        setShowHeader(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Inject view model factory and load view model

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSurveyViewModel = ViewModelProviders
                .of(getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

        setTitle("");

        setFooterColor(R.color.survey_darkyellow);
        setHeaderColor(R.color.survey_darkyellow);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_surveyintro, container, false);

        view.findViewById(R.id.btn_surveyintro_submit).setOnClickListener((event)->onSubmit());

        TextView familyNameTv = view.findViewById(R.id.tv_surveyintro_familyname);

        mSurveyViewModel.getCurrentFamily().observe(this, (family ->
                familyNameTv.setText(family.getName() + " Family")));

        return view;
    }

    void onSubmit(){

        mSurveyViewModel.getSurveys().observe(this, (surveys) ->
        {
            if(surveys.size()>0) {
                Survey survey = surveys.get(0);

                mSurveyViewModel.makeSnapshot(survey); //assumes family livedata object has value

                /**when snapshot is made**/
                mSurveyViewModel.getSnapshot().observe(this, (snapshot -> {
                    mSurveyViewModel.setSurveyState(SurveyState.BACKGROUND_QUESTIONS);
                }));
            }
            else
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(getString(R.string.survey_error_no_surveys_title))
                        .setContentText(getString(R.string.survey_error_no_surveys_description))
                        .setConfirmText(getString(R.string.all_okay))
                        .setConfirmClickListener((dialog)->
                        {
                            getActivity().finish();
                        })
                        .show();
            }
            //create snapshot with family and
        });
    }

    public static String getFragmentTag()
    {
        return FRAGMENT_TAG;
    }

    public static SurveyIntroFragment build()
    {
        SurveyIntroFragment fragment = new SurveyIntroFragment();

        return fragment;
    }
}