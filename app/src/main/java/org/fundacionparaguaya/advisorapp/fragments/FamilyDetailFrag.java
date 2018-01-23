package org.fundacionparaguaya.advisorapp.fragments;

import android.app.Fragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.activities.SurveyActivity;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.viewmodels.FamilyInformationViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;

import javax.inject.Inject;

/**
 * Created by Mone Elokda on 1/20/2018.
 */

public class FamilyDetailFrag extends StackedFrag implements Observer<Family> {
    private TextView mFamilyName;
    private TextView mAddress;
    private TextView mLocation;
    private SimpleDraweeView mFamilyImage;

    int mFamilyId;

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    FamilyInformationViewModel mFamilyInformationViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mFamilyInformationViewModel = ViewModelProviders
                .of((FragmentActivity) getActivity(), mViewModelFactory)
                .get(FamilyInformationViewModel.class);

        Bundle args = getArguments();
        mFamilyId = args.getInt("SELECTED_FAMILY");

        mFamilyInformationViewModel.getFamily(mFamilyId).observe(this, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.family_fragment, container, false);

        mFamilyName = (TextView) view.findViewById(R.id.family_view_name);
        mAddress = (TextView) view.findViewById(R.id.location_content);
        mLocation = (TextView) view.findViewById(R.id.description_content);
        mFamilyImage = (SimpleDraweeView) view.findViewById(R.id.family_image_2);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addButton  = view.findViewById(R.id.survey_button);

        addButton.setOnClickListener(view ){

        }
    }

    @Override
    public void onChanged(@Nullable Family family) {
        mFamilyName.setText(family.getName());
        mAddress.setText(family.getAddress());
        mLocation.setText((CharSequence) family.getLocation());

        Uri uri = Uri.parse("https://bongmendoza.files.wordpress.com/2012/08/urban-poor-family.jpg");
        mFamilyImage.setImageURI(uri);
    }
}
