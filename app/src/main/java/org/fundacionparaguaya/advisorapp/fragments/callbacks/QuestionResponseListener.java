package org.fundacionparaguaya.advisorapp.fragments.callbacks;

import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.Family;

import java.util.Objects;

/**
 * Created by Mone Elokda on 1/29/2018.
 */

public interface QuestionResponseListener {
    void onQuestionAnswered(BackgroundQuestion q, Object response);
    void onFamilyAdded(Family family);
}
