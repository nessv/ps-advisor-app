package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.viewmodels.AddFamilyViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class AddFamilyFrag extends StackedFrag {

    private AddFamilyAdapter mAddFamilyAdapter;

    private static String NEW_FAMILY_KEY = "SELECTED_FAMILY";

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    AddFamilyViewModel mAddFamilyViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mAddFamilyViewModel = ViewModelProviders
                .of((FragmentActivity) getActivity(), mViewModelFactory)
                .get(AddFamilyViewModel.class);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.addfamily_frag, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.addfaily_questions);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(mAddFamilyAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private class AddFamilyAdapter extends RecyclerView.Adapter {

        MutableLiveData<List<BackgroundQuestion>> questionsList = mAddFamilyViewModel.getQuestions();

        @Override
        public int getItemViewType(int position) {
            // Just as an example, return 0 or 2 depending on position
            // Note that unlike in ListView adapters, types don't have to be contiguous
            return position % 2 * 2;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_addfamilyquestion, parent, false);

            switch (viewType){
                case 0: return new ViewHolderString(parent);

            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)){
                case 0:
                    ViewHolderString viewHolderString =(ViewHolderString)holder;

            }


        }

        public class ViewHolderString extends RecyclerView.ViewHolder {


            LinearLayout familyInfoItem;
            TextView familyInfoQuestion;
            EditText familyInfoEntry;

            public ViewHolderString(View itemView) {
                super(itemView);
                familyInfoItem = (LinearLayout) itemView.findViewById(R.id.item_addfamily);
                familyInfoQuestion = (TextView) itemView.findViewById(R.id.addfamily_question);
                //familyInfoQuestion.setText(nameQuestion.getDescription());
                familyInfoEntry = (EditText) itemView.findViewById(R.id.entry_text_field);
                
            }

        }




        @Override
        public int getItemCount() {
            return 0;
        }
    }

    public static AddFamilyFrag build(int familyId)
    {
        Bundle args = new Bundle();
        args.putInt(NEW_FAMILY_KEY, familyId);
        AddFamilyFrag f = new AddFamilyFrag();
        f.setArguments(args);

        return f;
    }

}
