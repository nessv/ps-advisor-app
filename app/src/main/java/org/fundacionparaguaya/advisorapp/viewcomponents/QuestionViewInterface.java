package org.fundacionparaguaya.advisorapp.viewcomponents;

import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;

/**
 * Interface for interacting with background question views
 */

public interface QuestionViewInterface {
    void setQuestion(BackgroundQuestion q);
    void setResponse(String s);
}
