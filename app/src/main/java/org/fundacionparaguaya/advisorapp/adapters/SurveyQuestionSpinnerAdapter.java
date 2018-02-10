package org.fundacionparaguaya.advisorapp.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import org.fundacionparaguaya.advisorapp.models.Snapshot;

import java.util.Date;

public class SurveyQuestionSpinnerAdapter extends SelectedFirstSpinnerAdapter<String>
{
    SurveyQuestionSpinnerAdapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);

        setHasEmptyPlaceholder(true);
    }
}