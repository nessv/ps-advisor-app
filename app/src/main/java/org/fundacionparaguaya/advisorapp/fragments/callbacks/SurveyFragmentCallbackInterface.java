package org.fundacionparaguaya.advisorapp.fragments.callbacks;

import android.arch.lifecycle.LiveData;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.fundacionparaguaya.advisorapp.models.Snapshot;

/**
 * Callback for fragments inside of the survey activity. Gives fragments interface to interact
 * with the parent activity. Should be implemented by the parent activity
 */

public interface SurveyFragmentCallbackInterface extends NavigationListener
{
    /**
     * Update the progress displayed in the footer
     * This could possibly be replaced by wrapping the snapshot in a live data object
     *
     * @param questionsLeft String displayed in the bottom left
     * @param progress Setting for the progress bar
     */
    void onUpdateProgress(String questionsLeft, int progress);

    /**
     * Sets the color of the footer
     */
    void setHeaderFooterColor(Color headerFooterColor);

    /**
     * Sets the title of the header
     */
    void setTitle(String title);

    /**
     * Hides header in survey activity
     */
    void hideHeader();

    /**
     * Hides footer in survey activity
     */
    void hideFooter();

    /**
     * Show header in survey activity
     */
    void showHeader();

    /**
     * Hide header in survey activity
     */
    void showFooter();

    @Nullable
    LiveData<Snapshot> getSnapshot();

    /**
     * Should be called when the survey fragment is finished
     *
     * @param snap Snapshot with responses updated
     */
    void onFinish(@Nullable Snapshot snap);
}
