package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.fundacionparaguaya.advisorapp.R;

/**
 * Created by benhylak on 1/16/18.
 */

public class TestStackedFragment extends StackedFrag
{
    public static String BUNDLE_ID_TEXT_TO_DISPLAY = "TEXT_TO_DISPLAY";

    public String mDisplayText;
    public TextView mTextView;

    public Button mButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null && getArguments().getString(BUNDLE_ID_TEXT_TO_DISPLAY) != null)
        {
            mDisplayText = getArguments().getString(BUNDLE_ID_TEXT_TO_DISPLAY);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        mTextView = (TextView) rootView.findViewById(R.id.textView);

        mTextView.setText(mDisplayText);

        mButton = (Button)rootView.findViewById(R.id.button);

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        mButton.setOnClickListener((clickEvent) -> {
            NavigationEvent navigationEvent = new NavigationEvent(TestStackedFragment.build());
            notifyNavigtionHandlers(navigationEvent);
        });
    }

    public static StackedFrag build()
    {
        TestStackedFragment fragment = new TestStackedFragment();
        Bundle args = new Bundle();
        args.putString(TestStackedFragment.BUNDLE_ID_TEXT_TO_DISPLAY, "Fragment 2");
        fragment.setArguments(args);

        return fragment;
    }
}
