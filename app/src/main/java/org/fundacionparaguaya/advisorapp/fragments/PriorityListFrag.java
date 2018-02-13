package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.LifeMapPriority;
import org.fundacionparaguaya.advisorapp.util.IndicatorUtilities;
import org.fundacionparaguaya.advisorapp.viewcomponents.PriorityDetailPopupWindow;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Shows all of the indicators that a family has and their red/yellow/green status. Selecting one opens up a dialog,
 * that when filled out, adds the priority to the view model
 */

public class PriorityListFrag extends Fragment  {

    //TODO: this isn't enforced
    private static final int MAX_PRIORITIES = 5;

    private static final float INDICATOR_WIDTH = 140;
    private static final float INDICATOR_MARGIN = 56;

    private TextView mHeader;

    @Inject
    protected InjectionViewModelFactory mViewModelFactory;
    protected SharedSurveyViewModel mSharedSurveyViewModel;
    protected RecyclerView mRvIndicators;

    private PriorityDetailPopupWindow mPopup;

    PriorityListAdapter mPriorityAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSharedSurveyViewModel = ViewModelProviders.
                of(getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

        mPriorityAdapter = new PriorityListAdapter();

        mSharedSurveyViewModel.getPriorities().observe(this, (value) ->
        {
            if(value!=null && value.size()<MAX_PRIORITIES)
            {
                mHeader.setText(String.format(getString(R.string.prioritieslist_header_remaining),
                        MAX_PRIORITIES - value.size()));
            }
            else if(value!=null)
            {
                mHeader.setText(getString(R.string.prioritieslist_header_complete));
            }
            else
            {
                mHeader.setText(String.format(getString(R.string.prioritieslist_header_remaining),
                        MAX_PRIORITIES));
            }

            mPriorityAdapter.setPriorities(value);
        });

        mSharedSurveyViewModel.getIndicatorResponses().observe(this, mPriorityAdapter::setIndicators);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prioritylist, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.rv_prioritieslist);
        recyclerView.setAdapter(mPriorityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        v.findViewById(R.id.btn_prioritylist_save).setOnClickListener((view -> onSave()));

        mHeader = v.findViewById(R.id.tv_prioriitylist_header);

        return v;
    }

    public void onSave()
    {
        mSharedSurveyViewModel.saveSnapshotAsync();
    }

    static class PriorityListAdapter extends RecyclerView.Adapter
    {
        private List<IndicatorOption> mResponses = null;
        private List<LifeMapPriority> mPriorities = null;
        private Map<Indicator, IndicatorOption> mIndicatorOptionMap;

    //    private PriorityDetailPopupWindow.PriorityPopupResponseCallback mCallback;

        public void setIndicators(Collection<IndicatorOption> responses)
        {
            if(mResponses==null && responses!=null) {
                mResponses = new ArrayList<>(responses);
            }

            mIndicatorOptionMap = new HashMap<>();

            for(IndicatorOption option: responses)
            {
                mIndicatorOptionMap.put(option.getIndicator(), option);
            }

            notifyDataSetChanged();
        }

        public void setPriorities(List<LifeMapPriority> priorities)
        {
            mPriorities = priorities;

            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prioritylist, parent, false);

            return new PriorityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            LifeMapPriority priority = mPriorities.get(position);
            IndicatorOption response = mIndicatorOptionMap.get(priority.getIndicator());

            PriorityViewHolder priorityViewHolder = ((PriorityViewHolder) holder);

            priorityViewHolder.setPriority(response, priority);
        }


        @Override
        public int getItemCount() {
            if(mPriorities==null) return 0;
            return mPriorities.size();
        }

        static class PriorityViewHolder extends RecyclerView.ViewHolder {
            private TextView mTitle;
            private TextView mProblem;
            private TextView mStrategy;
            private TextView mWhen;
            private AppCompatImageView mIndicatorColor;

            PriorityViewHolder(View itemView) {
                super(itemView);

                mTitle = itemView.findViewById(R.id.tv_priorityitem_title);
                mProblem = itemView.findViewById(R.id.tv_priorityitem_problem);
                mStrategy = itemView.findViewById(R.id.tv_priorityitem_strategy);
                mWhen = itemView.findViewById(R.id.tv_priorityitem_when);
                mIndicatorColor = itemView.findViewById(R.id.iv_priorityitem_color);
            }

            void setPriority(IndicatorOption option, LifeMapPriority p) {
                IndicatorUtilities.setViewColorFromResponse(option, mIndicatorColor);
                mTitle.setText(getAdapterPosition()+1 + ". " + p.getIndicator().getTitle());

                if(p.getReason()!=null) {
                    mProblem.setText(p.getReason());
                }

                if(p.getAction()!=null) {
                    mStrategy.setText(p.getAction());
                }

                if(p.getEstimatedDate()!=null) {
                    String when = SimpleDateFormat.getDateInstance().format(p.getEstimatedDate());
                    mWhen.setText(when);
                }
            }
        }
        }
    }