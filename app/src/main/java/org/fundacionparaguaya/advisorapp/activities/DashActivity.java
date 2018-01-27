package org.fundacionparaguaya.advisorapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.curioustechizen.ago.RelativeTimeTextView;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.ExampleTabbedFragment;
import org.fundacionparaguaya.advisorapp.fragments.FamilyTabbedFragment;
import org.fundacionparaguaya.advisorapp.fragments.TabbedFrag;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.DisplayBackNavListener;
import org.fundacionparaguaya.advisorapp.repositories.SyncManager;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTab;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTabBarView;

import javax.inject.Inject;

public class DashActivity extends AbstractFragSwitcherActivity implements DisplayBackNavListener
{
    private final Handler mHandler = new Handler();

    DashboardTabBarView tabBarView;
    TextView mSyncLabel;
    ImageButton mSyncButton;
    RelativeTimeTextView mLastSyncTextView;

    TabbedFrag mFamiliesFrag;
    TabbedFrag mMapFrag;
    TabbedFrag mArchiveFrag;
    TabbedFrag mSettingsFrag;

    @Inject
    SyncManager mSyncManager;

	@Override
    public void onBackPressed()
    {
        getFragForType(tabBarView.getSelected()).onNavigateBack();
    }

    private TabbedFrag getFragForType(DashboardTab.TabType type) {
        switch (type)
        {
            case FAMILY:
                return mFamiliesFrag;
            case MAP:
                return mMapFrag;
            case ARCHIVE:
                return mArchiveFrag;
            case SETTINGS:
                return mSettingsFrag;
        }

        return null;
    }

    private DashboardTabBarView.TabSelectedHandler mTabSelectedHandler = (event) ->
    {
        switchToFrag(getFragForType(event.getSelectedTab()));

        Toast.makeText(getApplicationContext(), event.getSelectedTab().name(), Toast.LENGTH_SHORT).show();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) this.getApplication())
                .getApplicationComponent()
                .inject(this);

        setContentView(R.layout.activity_main);

	    tabBarView = (DashboardTabBarView) findViewById(R.id.dashboardTabView);
        tabBarView.addTabSelectedHandler(mTabSelectedHandler);

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

        /**
         * Create fragment for each tab
         */
        mFamiliesFrag = new FamilyTabbedFragment();
        mMapFrag = new ExampleTabbedFragment();
        mArchiveFrag = new ExampleTabbedFragment();
        mSettingsFrag = new ExampleTabbedFragment();

        initFragSwitcher(R.id.dash_content, mFamiliesFrag, mMapFrag);

        switchToFrag(mFamiliesFrag);
    }

    private void onSyncButtonPress(View view) {
        new SyncRepositoryTask().execute();
    }

    @Override
    public void onShowBackNav()
    {
        Toast.makeText(getApplicationContext(), "Show Back Nav", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideBackNav()
    {
        Toast.makeText(getApplicationContext(), "Hide Back Nav", Toast.LENGTH_SHORT).show();
    }

    private class SyncRepositoryTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            mSyncLabel.setText(R.string.topbar_synclabel_syncing);
            mSyncButton.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(Void... Void) {
            return mSyncManager.sync();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mSyncButton.setEnabled(true);
            mSyncLabel.setText(R.string.topbar_synclabel);
        }
    }
}

