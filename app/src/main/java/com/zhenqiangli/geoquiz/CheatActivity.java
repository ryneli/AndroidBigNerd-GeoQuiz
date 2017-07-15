package com.zhenqiangli.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheatActivity extends AppCompatActivity {
    private static int cheatRemain = 3;
    @BindView(R.id.tv_cheat_question)
    TextView question;
    @BindView(R.id.button_cheat_answer)
    Button answerButton;
    @BindView(R.id.tv_api_level)
    TextView tvApiLevel;

    private static final String INTENT_QUESTION = "question";
    private static final String INTENT_ANSWER = "answer";
    public static final String INTENT_ANSWER_SHOWN = "shown";
    public static Intent newIntent(Context context, String question, boolean answer) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(INTENT_QUESTION, question);
        intent.putExtra(INTENT_ANSWER, answer);
        return intent;
    }
    private String questionString;
    private boolean answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        ButterKnife.bind(this);
        questionString = getIntent().getStringExtra(INTENT_QUESTION);
        answer = getIntent().getBooleanExtra(INTENT_ANSWER, false);
        question.setText(questionString);
        tvApiLevel.setText("API Level " + Build.VERSION.SDK_INT);
    }

    @OnClick(R.id.button_cheat_answer)
    public void onClickCheatAnswer(View v) {
        if (cheatRemain <= 0) {
            Toast.makeText(this, "Maximum cheat", Toast.LENGTH_SHORT).show();
            return;
        }

        cheatRemain--;
        question.setText("Answer is " + answer);
        setAnswerShownResult(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = answerButton.getWidth() / 2;
            int cy = answerButton.getHeight() / 2;
            float radius = answerButton.getWidth();
            Animator anim = ViewAnimationUtils
                    .createCircularReveal(answerButton, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    answerButton.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            answerButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnswerShownResult(boolean shown) {
        Intent data = new Intent();
        data.putExtra(INTENT_ANSWER_SHOWN, shown);
        setResult(RESULT_OK, data);
    }
}
