package org.fundacionparaguaya.advisorapp.activities;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.github.curioustechizen.ago.RelativeTimeTextView;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.*;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.DisplayBackNavListener;
import org.fundacionparaguaya.advisorapp.repositories.SyncManager;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTab;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTabBarView;

import javax.inject.Inject;

public class DashActivity extends AbstractFragSwitcherActivity implements DisplayBackNavListener
{
    DashboardTabBarView tabBarView;
    TextView mSyncLabel;
    ImageButton mSyncButton;
    RelativeTimeTextView mLastSyncTextView;

    LinearLayout mBackButton;

    @Inject
    SyncManager mSyncManager;

    ObjectAnimator mSyncRotateAnimation;

    static String SELECTED_TAB_KEY = "SELECTED_TAB";


    //if display back button = false
    ///display title if it has a title

    //if display back button = true
    //display title, if it has a title
    //else display "Back"

    @Override
    public void onBackPressed() {
        ((AbstractTabbedFrag) getFragment(getClassForType(tabBarView.getSelected()))).onNavigateBack();
    }

    private Class getClassForType(DashboardTab.TabType type) {
        switch (type) {
            case FAMILY:
                return FamilyTabbedFragment.class;
            case MAP:
                return MapTabFrag.class;
            case ARCHIVE:
                return ArchiveTabFrag.class;
            case SETTINGS:
                return SettingsTabFrag.class;
        }

        return null;
    }

    private DashboardTabBarView.TabSelectedHandler handler = (event) ->
    {
        switchToFrag(getClassForType(event.getSelectedTab()));
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save selected tab
        outState.putString(SELECTED_TAB_KEY, tabBarView.getSelected().name());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) this.getApplication())
                .getApplicationComponent()
                .inject(this);

        setContentView(R.layout.activity_main);

        setFragmentContainer(R.id.dash_content);

        tabBarView = (DashboardTabBarView) findViewById(R.id.dashboardTabView);

        mSyncLabel = findViewById(R.id.topbar_synclabel);
        mLastSyncTextView = findViewById(R.id.last_sync_textview);

        mSyncButton = findViewById(R.id.dashboardtopbar_syncbutton);
        mSyncButton.setOnClickListener(this::onSyncButtonPress);

        //update last sync label when the sync manager updates
        mSyncManager.getLastSyncedTime().observe(this, (value)->
        {
            if(value != -1) {
                mLastSyncTextView.setReferenceTime(value);
            }
        });

        if (savedInstanceState != null) {
            String selectTypeName = savedInstanceState.getString(SELECTED_TAB_KEY);

            if (selectTypeName != null) {
                DashboardTab.TabType previouslySelected = DashboardTab.TabType.valueOf(selectTypeName);
                tabBarView.selectTab(previouslySelected);
                switchToFrag(getClassForType(previouslySelected));
            }
        }
        else
        {
            switchToFrag(FamilyTabbedFragment.class);
        }

        mBackButton = findViewById(R.id.linearlayout_dashactivity_back);
        mBackButton.setVisibility(View.GONE);
        mBackButton.setOnClickListener((event)-> onBackPressed());

        tabBarView.addTabSelectedHandler(handler);

        ImageView fpLogo = findViewById(R.id.fp_logo);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        if(metrics.ydpi < 600 && fpLogo !=null)
        {
            fpLogo.setVisibility(View.GONE);
        }
    }

    private void onSyncButtonPress(View view) {
        new SyncRepositoryTask().execute();
    }

    @Override
    public void onShowBackNav() {
       mBackButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideBackNav() {
        mBackButton.setVisibility(View.GONE);
    }

    private class SyncRepositoryTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            mSyncLabel.setText(R.string.topbar_synclabel_syncing);


            mSyncRotateAnimation= ObjectAnimator.ofFloat(mSyncButton,
                    "rotation", 0f, 360f);
            mSyncRotateAnimation.setRepeatCount(ObjectAnimator.INFINITE);
            mSyncRotateAnimation.setRepeatMode(ObjectAnimator.RESTART);
            mSyncRotateAnimation.setDuration(1000);
            mSyncRotateAnimation.setInterpolator(new LinearInterpolator());
            mSyncRotateAnimation.start();

            mSyncButton.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(Void... Void) {
            return mSyncManager.sync();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mSyncButton.setEnabled(true);
            mSyncRotateAnimation.cancel();
            mSyncLabel.setText(R.string.topbar_synclabel);
        }
    }
}

