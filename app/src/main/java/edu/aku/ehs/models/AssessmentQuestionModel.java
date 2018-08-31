package edu.aku.ehs.models;

import edu.aku.ehs.enums.QuestionTypeEnum;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class AssessmentQuestionModel {

    private String question1;
    private boolean answer1 = false;
    private boolean answer2 = false;
    private QuestionTypeEnum questionTypeEnum;

    public AssessmentQuestionModel(String question1, QuestionTypeEnum questionTypeEnum) {
        this.question1 = question1;
        this.questionTypeEnum = questionTypeEnum;
    }


    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public boolean isAnswer1() {
        return answer1;
    }

    public void setAnswer1(boolean answer1) {
        this.answer1 = answer1;
    }

    public boolean isAnswer2() {
        return answer2;
    }

    public void setAnswer2(boolean answer2) {
        this.answer2 = answer2;
    }

    public QuestionTypeEnum getQuestionTypeEnum() {
        return questionTypeEnum;
    }

    public void setQuestionTypeEnum(QuestionTypeEnum questionTypeEnum) {
        this.questionTypeEnum = questionTypeEnum;
    }
}
