package org.fundacionparaguaya.advisorapp.fragments;

import android.app.Fragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.viewmodels.AllFamiliesViewModel;

import java.util.List;

/**
 * Created by Mone Elokda on 1/13/2018.
 */

public class AllFamiliesFragment extends Fragment {



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

        /*ListAdapter listAdapter = new ListAdapter(families);
        recyclerView.setAdapter(listAdapter);*/
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}



