package com.zhenqiangli.geoquiz.data;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public class Question {
    private String question;
    private boolean answer;
    public Question(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isAnswer() {
        return answer;
    }
}