package org.fundacionparaguaya.advisorapp.fragments;

import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.FamilyAdapter;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.viewmodels.AllFamiliesViewModel;


import java.util.List;

/**
 * Created by Mone Elokda on 1/13/2018.
 */

public class AllFamiliesFragment extends Fragment implements View.OnClickListener {

    private FamilyAdapter mFamilyAdapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        AllFamiliesViewModel viewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(AllFamiliesViewModel.class);
        LiveData<List<Family>> families = viewModel.getFamily();


    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.families_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.all_families_view);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    private void subscribeUi(AllFamiliesViewModel viewModel){
        viewModel.getFamily().observe((LifecycleOwner) this, new Observer<List<Family>>() {
            @Override
            public void onChanged(@Nullable List<Family> families) {
                if(families != null ){
                    mFamilyAdapter.setmFamilyList(families);
                }

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}



