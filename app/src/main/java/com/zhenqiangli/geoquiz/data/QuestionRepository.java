package com.zhenqiangli.geoquiz.data;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public class QuestionRepository implements QuestionSource {
    private Question[] mQuestionBank = new Question[] {
            new Question("Beijing is the capital of China", true),
            new Question("You are the best", false),
    };
    private int i = 0;

    @Override
    public Question getNextQuestion() {
        Question question = mQuestionBank[i];
        if (i != mQuestionBank.length-1) {
            i++;
        } else {
            i = 0;
        }
        return question;
    }
}
