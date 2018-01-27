package org.fundacionparaguaya.advisorapp.viewcomponents;

import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;
import org.fundacionparaguaya.advisorapp.models.SurveyQuestion;

/**
 * Interface for interacting with background question views
 */

public interface QuestionViewInterface {
    void setQuestion(PersonalQuestion q);
    void setQuestion(EconomicQuestion q);
    void setResponse(String s);
}
