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

    @Inject
    SyncManager mSyncManager;

    static String SELECTED_TAB_KEY = "SELECTED_TAB";

    @Override
    public void onBackPressed() {
        ((TabbedFrag) getFragment(getClassForType(tabBarView.getSelected()))).onNavigateBack();
    }

    private Class getClassForType(DashboardTab.TabType type) {
        switch (type) {
            case FAMILY:
                return FamilyTabbedFragment.class;
            case MAP:
                return ExampleTabbedFragment.class;
            case ARCHIVE:
                return ExampleTabbedFragment.class;
            case SETTINGS:
                return ExampleTabbedFragment.class;
        }

        return null;
    }

    private DashboardTabBarView.TabSelectedHandler handler = (event) ->
    {
        switchToFrag(getClassForType(event.getSelectedTab()));

        Toast.makeText(getApplicationContext(), event.getSelectedTab().name(), Toast.LENGTH_SHORT).show();
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

        tabBarView.addTabSelectedHandler(handler);
    }

    private void onSyncButtonPress(View view) {
        new SyncRepositoryTask().execute();
    }

    @Override
    public void onShowBackNav() {
        Toast.makeText(getApplicationContext(), "Show Back Nav", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideBackNav() {
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

