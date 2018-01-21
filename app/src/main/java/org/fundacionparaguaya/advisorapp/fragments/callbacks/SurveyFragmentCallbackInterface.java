package org.fundacionparaguaya.advisorapp.fragments.callbacks;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.fundacionparaguaya.advisorapp.models.Snapshot;

/**
 * Created by benhylak on 1/21/18.
 */

public interface SurveyFragmentCallbackInterface extends NavigationListener
{
    void onUpdateProgress(String questionsLeft, int progress);

    void setHeaderFooterColor();

    void setTitle();

    void hideHeader();

    void hideFooter();

    void showHeader();

    void showFooter();

    @Nullable
    LiveData<Snapshot> getSnapshot();

    void onFinish(Snapshot snap);
}
