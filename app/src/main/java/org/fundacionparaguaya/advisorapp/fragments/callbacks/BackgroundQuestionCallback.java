package org.fundacionparaguaya.advisorapp.fragments.callbacks;

import android.view.View;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.Family;

import java.util.Objects;

/**
 * A callback for fragments that are displaying a list of background questions
 */

public interface BackgroundQuestionCallback {
    void onQuestionAnswered(BackgroundQuestion q, Object response);
    void onNext(View v);
    void onSubmit();
    String getResponseFor(BackgroundQuestion q);
}
