package org.fundacionparaguaya.advisorapp.fragments;

import android.app.Activity;
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
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.LifeMapPriority;
import org.fundacionparaguaya.advisorapp.util.IndicatorUtilities;
import org.fundacionparaguaya.advisorapp.util.ScreenCalculations;
import org.fundacionparaguaya.advisorapp.viewcomponents.PriorityDetailPopupWindow;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import javax.inject.Inject;
import java.util.*;

/**
 * Shows all of the indicators that a family has and their red/yellow/green status. Selecting one opens up a dialog,
 * that when filled out, adds the priority to the view model
 */

public class LifeMapFragment extends Fragment implements PriorityDetailPopupWindow.PriorityPopupResponseCallback
{

    private static final float INDICATOR_WIDTH = 140;
    private static final float INDICATOR_MARGIN = 56;

    @Inject
    protected InjectionViewModelFactory mViewModelFactory;
    protected SharedSurveyViewModel mSharedSurveyViewModel;
    protected RecyclerView mRvIndicators;
    protected LifeMapIndicatorAdapter mIndicatorAdapter;

    private PriorityDetailPopupWindow mPopup;

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
        mIndicatorAdapter.setPopupCallback(this);

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

    @Override
    public void onPriorityPopupFinished(PriorityDetailPopupWindow window, PriorityDetailPopupWindow.PriorityPopupFinishedEvent e) {
        window.dismiss();

        switch (e.getResultType())
        {
            case ADD:
            {
                mSharedSurveyViewModel.addPriority(e.getNewPriority());
                Toast.makeText(getContext(), "Added New Priority", Toast.LENGTH_SHORT).show();
                break;
            }
            case REPLACE:
            {
                mSharedSurveyViewModel.removePriority(e.getOriginalPriority());
                mSharedSurveyViewModel.addPriority(e.getNewPriority());
                break;
            }
        }
    }

    static class LifeMapIndicatorAdapter extends RecyclerView.Adapter
    {
        private List<IndicatorOption> mResponses = null;
        private List<LifeMapPriority> mPriorities = null;
        private PriorityDetailPopupWindow.PriorityPopupResponseCallback mCallback;

        public void setIndicators(Collection<IndicatorOption> responses)
        {
            if(mResponses==null && responses!=null)
            {
                mResponses = IndicatorUtilities.getResponsesAscending(responses);
            }

            notifyDataSetChanged();
        }

        public void setPopupCallback(PriorityDetailPopupWindow.PriorityPopupResponseCallback callback)
        {
            mCallback = callback;
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
            LifeMapIndicatorViewHolder holder =  new LifeMapIndicatorViewHolder(view);
            holder.setCallback(mCallback);
            return holder;
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

            private IndicatorOption mResponse;
            private LifeMapPriority mLifeMapPriority = null;

            PriorityDetailPopupWindow mPopupWindow;


            PriorityDetailPopupWindow.PriorityPopupResponseCallback mCallback;

            public LifeMapIndicatorViewHolder(View itemView) {
                super(itemView);

                mColor = itemView.findViewById(R.id.iv_indicatoritem_color);
                mTitle = itemView.findViewById(R.id.tv_lifemapindicator_name);
                mNumber =itemView.findViewById(R.id.tv_lifemapindicator_number);

                mNumber.setVisibility(View.INVISIBLE);
                itemView.setBackground(null);

            }

            public void setCallback(PriorityDetailPopupWindow.PriorityPopupResponseCallback c)
            {
                mCallback = c;
            }

            public void setSelectedBackground()
            {
                itemView.setBackground(ContextCompat.getDrawable(itemView.getContext(),
                        R.drawable.lifemapindicator_background));
            }

            public void setUnselectedBackground()
            {
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
                mPopupWindow.setPriority(priority);

                isPriority = true;
            }

            public void setResponse(IndicatorOption response)
            {
                isPriority = false;

                mResponse = response;

                IndicatorUtilities.setViewColorFromResponse(response, mColor);

                String title = response.getIndicator().getTitle();
                mTitle.setText(title);

                mPopupWindow = new PriorityDetailPopupWindow.Builder(itemView.getContext()).
                        setIndicatorOption(response).setResponseCallback(mCallback).build();

                itemView.setOnClickListener((view)->{
                    if(mResponse.getLevel()== IndicatorOption.Level.Green)
                    {
                        Toast.makeText(view.getContext(), R.string.prioritychooser_greenselected, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mPopupWindow.show();
                    }
                });
            }
        }

    }
}
