package com.zhenqiangli.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhenqiangli.geoquiz.data.Question;
import com.zhenqiangli.geoquiz.data.QuestionRepository;
import com.zhenqiangli.geoquiz.data.QuestionSource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends AppCompatActivity {
    private QuestionSource questionSource = new QuestionRepository();
    private Question currentQuestion;
    private int indexQuestion = 0;
    private int REQUEST_CHEAT_ACTIVITY = 1;

    @BindView(R.id.tv_question) TextView questionText;
    @BindView(R.id.button_true) Button trueButton;
    @BindView(R.id.button_false) Button falseButton;
    @BindView(R.id.button_next) Button nextButton;
    @BindView(R.id.button_cheat) Button cheatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        currentQuestion = questionSource.getNextQuestion();
        setQuestion(currentQuestion);
    }

    private void setQuestion(Question question) {
        questionText.setText(question.getQuestion());
    }

    @OnClick(R.id.button_next)
    void onClickNext(View v) {
        currentQuestion = questionSource.getNextQuestion();
        setQuestion(currentQuestion);
    }

    @OnClick(R.id.button_true)
    void onClickTrue(View v) {
        if (currentQuestion.isAnswer()) {
            Toast.makeText(this, "Right!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.button_false)
    void onClickFalse(View v) {
        if (!currentQuestion.isAnswer()) {
            Toast.makeText(this, "Right!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.button_cheat)
    void onClickCheat(View v) {
        Intent intent = CheatActivity.newIntent(
                this, currentQuestion.getQuestion(), currentQuestion.isAnswer());
        startActivityForResult(intent, REQUEST_CHEAT_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean shown = false;
        if (data != null) {
            shown = data.getBooleanExtra(CheatActivity.INTENT_ANSWER_SHOWN, false);
        }
        
        if (shown) {
            Toast.makeText(this, "Answer is shown.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Answer isn't shown.", Toast.LENGTH_SHORT).show();
        }
    }
}
