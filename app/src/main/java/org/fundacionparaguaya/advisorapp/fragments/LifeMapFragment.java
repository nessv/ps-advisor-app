package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.LifeMapPriority;
import org.fundacionparaguaya.advisorapp.util.IndicatorUtilities;
import org.fundacionparaguaya.advisorapp.util.ScreenCalculations;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import javax.inject.Inject;
import java.util.*;

/**
 * Shows all of the indicators that a family has and their red/yellow/green status. Selecting one opens up a dialog,
 * that when filled out, adds the priority to the view model
 */

public class LifeMapFragment extends AbstractSurveyFragment{

    private static final float INDICATOR_WIDTH = 140;
    private static final float INDICATOR_MARGIN = 56;

    @Inject
    protected InjectionViewModelFactory mViewModelFactory;
    protected SharedSurveyViewModel mSharedSurveyViewModel;
    protected RecyclerView mRvIndicators;
    protected LifeMapIndicatorAdapter mIndicatorAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSharedSurveyViewModel = ViewModelProviders.
                of(getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

        mIndicatorAdapter = new LifeMapIndicatorAdapter();

        mSharedSurveyViewModel.getPriorities().observe(this, mIndicatorAdapter::setPriorities);
        mSharedSurveyViewModel.getIndicatorResponses().observe(this, mIndicatorAdapter::setIndicators);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lifemap, container, false);

        mRvIndicators = v.findViewById(R.id.rv_lifemap_indicators);
        mRvIndicators.setLayoutManager(new GridLayoutManager(getContext(),
                ScreenCalculations.calculateNoOfColumns(INDICATOR_WIDTH, INDICATOR_MARGIN, getContext())));
        mRvIndicators.setAdapter(mIndicatorAdapter);

        return v;
    }

    static class LifeMapIndicatorAdapter extends RecyclerView.Adapter
    {
        private List<IndicatorOption> mResponses = null;
        private List<LifeMapPriority> mPriorities = null;

        public void setIndicators(Collection<IndicatorOption> responses)
        {
            if(mResponses==null && responses!=null)
            {
                mResponses = IndicatorUtilities.getResponsesAscending(responses);
            }

            notifyDataSetChanged();
        }

        public void setPriorities(List<LifeMapPriority> priorities)
        {
            mPriorities = priorities;
            //should do diff here

            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lifemapindicator, parent, false);
            return new LifeMapIndicatorViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            LifeMapIndicatorViewHolder lifeMapHolder = ((LifeMapIndicatorViewHolder) holder);

            IndicatorOption response = mResponses.get(position);

            int priorityIndex = IndicatorUtilities.isPriority(response.getIndicator(), mPriorities);

            if (priorityIndex != -1) {
                lifeMapHolder.setAsPriority(response, mPriorities.get(priorityIndex), priorityIndex);
            }
            else {
                lifeMapHolder.setResponse(response);
            }
        }


        @Override
        public int getItemCount() {
            if(mResponses == null)
            {
                return 0;
            }
            else
            {
                return mResponses.size();
            }
        }

        static class LifeMapIndicatorViewHolder extends RecyclerView.ViewHolder
        {
            private AppCompatImageView mColor;
            private TextView mTitle;
            private TextView mNumber;
            private boolean isPriority;

            private LifeMapPriority mLifeMapPriority = null;

            public LifeMapIndicatorViewHolder(View itemView) {
                super(itemView);

                mColor = itemView.findViewById(R.id.iv_indicatoritem_color);
                mTitle = itemView.findViewById(R.id.tv_lifemapindicator_name);
                mNumber =itemView.findViewById(R.id.tv_lifemapindicator_number);

                mNumber.setVisibility(View.INVISIBLE);
                itemView.setBackground(null);
            }

            /**
             * Changes the background of this view holder to the selected state
             * and displays the priority order
             *
             * @param number The ordering of the priority (1-5)
             */
            public void setAsPriority(IndicatorOption response, LifeMapPriority priority, int number)
            {
                setResponse(response);

                itemView.setBackground(ContextCompat.getDrawable(itemView.getContext(),
                        R.drawable.lifemapindicator_background));

                mLifeMapPriority = priority;

                isPriority = true;
            }

            public void setResponse(IndicatorOption response)
            {
                isPriority = false;

                if(response.getIndicator()!=null) {
                    mTitle.setText(response.getIndicator().getName());
                }
                else mTitle.setText("No Indicator Set");

                IndicatorUtilities.setViewColorFromResponse(response, mColor);
            }
        }

    }
}
