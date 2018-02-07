package org.fundacionparaguaya.advisorapp.activities;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
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
import org.fundacionparaguaya.advisorapp.jobs.SyncJob;
import org.fundacionparaguaya.advisorapp.repositories.SyncManager;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTab;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTabBarView;
import org.w3c.dom.Text;

import javax.inject.Inject;

import static org.fundacionparaguaya.advisorapp.repositories.SyncManager.SyncState.NEVER;
import static org.fundacionparaguaya.advisorapp.repositories.SyncManager.SyncState.SYNCING;

public class DashActivity extends AbstractFragSwitcherActivity implements DisplayBackNavListener
{
    DashboardTabBarView tabBarView;
    TextView mSyncLabel;
    LinearLayout mSyncButton;
    ImageView mSyncButtonIcon;
    RelativeTimeTextView mLastSyncTextView;
    TextView mTvTabTitle;
    TextView mTvBackLabel;

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
    protected void switchToFrag(Class fragmentClass) {
        super.switchToFrag(fragmentClass);

        String title = ((AbstractTabbedFrag)getFragment(fragmentClass)).getTabTitle();
        mTvTabTitle.setText(title);
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

        mTvTabTitle = findViewById(R.id.tv_topbar_tabtitle);
        mTvBackLabel = findViewById(R.id.tv_topbar_backlabel);

        mSyncButton = (LinearLayout) findViewById(R.id.dashboardtopbar_sync);
        mSyncButton.setOnClickListener(this::onSyncButtonPress);

        mSyncButtonIcon = findViewById(R.id.dashboardtopbar_syncbutton);

        //update last sync label when the sync manager updates
        mSyncManager.getProgress().observe(this, (value) -> {
            if (value != null) {
                if (value.getSyncState() == NEVER) {
                    mLastSyncTextView.setText(R.string.topbar_lastsync_never);
                } else {
                    mLastSyncTextView.setReferenceTime(value.getLastSyncedTime());
                }

                if (value.getSyncState() == SYNCING) {
                    mSyncLabel.setText(R.string.topbar_synclabel_syncing);
                    mSyncButton.setEnabled(false);
                } else {
                    mSyncButton.setEnabled(true);
                    mSyncLabel.setText(R.string.topbar_synclabel);
                }
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

        if(convertPixelsToDp(metrics.heightPixels, getApplicationContext())<600 && fpLogo !=null)
        {
            fpLogo.setVisibility(View.GONE);
        }
    }

    private void onSyncButtonPress(View view) {
        SyncJob.sync();
    }

    @Override
    public void onShowBackNav() {
       mBackButton.setVisibility(View.VISIBLE);
       mTvBackLabel.setText(mTvTabTitle.getText());
       mTvTabTitle.setVisibility(View.GONE);
    }

    @Override
    public void onHideBackNav() {
        mBackButton.setVisibility(View.GONE);
        mTvTabTitle.setVisibility(View.VISIBLE);
    }

    private class SyncRepositoryTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            mSyncLabel.setText(R.string.topbar_synclabel_syncing);


            mSyncRotateAnimation= ObjectAnimator.ofFloat(mSyncButtonIcon,
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

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}

