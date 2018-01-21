package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.FamilyAdapter;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.viewmodels.AllFamiliesViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Mone Elokda on 1/13/2018.
 */

public class AllFamiliesStackedFrag extends StackedFrag implements View.OnClickListener {
    private FamilyAdapter mFamilyAdapter;

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    AllFamiliesViewModel mAllFamiliesViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mAllFamiliesViewModel = ViewModelProviders
                .of((FragmentActivity) getActivity(), mViewModelFactory)
                .get(AllFamiliesViewModel.class);
        LiveData<List<Family>> families = mAllFamiliesViewModel.getFamilies();


        mFamilyAdapter = new FamilyAdapter(families.getValue());

        families.observe(this, (familiesList) -> {
           mFamilyAdapter.setFamilyList(familiesList);
        });

        mFamilyAdapter.addFamilySelectedHandler(new FamilyAdapter.FamilySelectedHandler() {
            @Override
            public void onFamilySelected(FamilyAdapter.FamilySelectedEvent e) {
                //String FamilyName = e.getSelectedFamily().getName();
                //Toast.makeText(getContext(),FamilyName + "Family Selected", Toast.LENGTH_LONG).show();

                Bundle args = getArguments();
                int s = args.getInt("SELECTED_FAMILY");
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton button  = view.findViewById(R.id.add_families_button);
        button.setOnClickListener((event) ->
        {
            mAllFamiliesViewModel.sync();
            mAllFamiliesViewModel.getFamilies().observe(this,
                    (familiesList) -> mFamilyAdapter.setFamilyList(familiesList));

            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.families_fragment, container, false);

        ImageButton addFamilyButton = (ImageButton) view.findViewById(R.id.add_families_button);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.all_families_view);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mFamilyAdapter);

        return view;
    }

    private void subscribeUi(AllFamiliesViewModel viewModel){
        viewModel.getFamilies().observe((LifecycleOwner) this, new Observer<List<Family>>() {
            @Override
            public void onChanged(@Nullable List<Family> families) {
            if(families != null ){
                mFamilyAdapter.setFamilyList(families);
            }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}



