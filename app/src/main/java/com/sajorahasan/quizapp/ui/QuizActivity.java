package com.sajorahasan.quizapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sajorahasan.quizapp.R;
import com.sajorahasan.quizapp.model.QuizEvent;
import com.sajorahasan.quizapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";

    @BindView(R.id.tvQueNo)
    TextView tvQueNo;
    @BindView(R.id.quizQuestion)
    TextView tvQuizQue;
    @BindView(R.id.answerRadioGroup)
    RadioGroup answerRadioGroup;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.tvCorrectAns)
    TextView tvCorrectAns;
    @BindView(R.id.tvInCorrectAns)
    TextView tvInCorrectAns;
    private int currentQueNo;
    private int totalQueNo;
    private int correct;
    private int incorrect;
    private ArrayList<QuizEvent> quizList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ButterKnife.bind(this);

        //Initializing Views
        initViews();

    }

    private void initViews() {
        quizList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            quizList = bundle.getParcelableArrayList("quizData");
        }

        if (quizList != null && quizList.size() != 0) {
            totalQueNo = quizList.size();
            Log.d(TAG, "initViews: Total   ---> " + totalQueNo);
            Log.d(TAG, "initViews: Current ---> " + currentQueNo);
            showNewQuestion(quizList.get(currentQueNo));
        }

        // Ripple Effect
        Tools.rippleWhite(btnNext);

        btnNext.setOnClickListener(view -> {
            if (quizList != null) {
                int selectedId = answerRadioGroup.getCheckedRadioButtonId();
                RadioButton rbAns = findViewById(selectedId);
                String ans = null;
                if (rbAns != null && rbAns.isChecked()) {
                    ans = rbAns.getText().toString();
                }

                if (ans != null && currentQueNo < totalQueNo) {
                    if (quizList.get(currentQueNo - 1).getCorrectAnswer().equalsIgnoreCase(ans)) {
                        correct++;
                        tvCorrectAns.setText(String.valueOf(correct));
                        Log.d(TAG, "initViews: correct ans -->" + String.valueOf(correct));
                    } else {
                        incorrect++;
                        quizList.get(currentQueNo - 1).setIncorrectQueNum(currentQueNo - 1);
                        quizList.get(currentQueNo - 1).setIncorrectAns(ans);
                        tvInCorrectAns.setText(String.valueOf(incorrect));
                        Log.d(TAG, "initViews: incorrect ans -->" + String.valueOf(correct));
                    }
                    showNewQuestion(quizList.get(currentQueNo));
                } else {
                    if (currentQueNo == totalQueNo) {
                        if (quizList.get(currentQueNo - 1).getCorrectAnswer().equalsIgnoreCase(ans)) {
                            correct++;
                            tvCorrectAns.setText(String.valueOf(correct));
                        } else {
                            incorrect++;
                            quizList.get(currentQueNo - 1).setIncorrectQueNum(currentQueNo - 1);
                            quizList.get(currentQueNo - 1).setIncorrectAns(ans);
                            tvInCorrectAns.setText(String.valueOf(incorrect));
                        }
                        Bundle b = new Bundle();
                        b.putParcelableArrayList("quizData", quizList);
                        b.putInt("correctAns", correct);
                        b.putInt("incorrectAns", incorrect);
                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);
                    } else {
                        Tools.alertError(QuizActivity.this, "Please select the answer first");
                    }
                }

            }
        });
    }

    public void showNewQuestion(QuizEvent quiz) {
        tvQueNo.setText(String.format("Question %s", String.valueOf(quiz.getQuestionNumber())));
        tvQuizQue.setText(quiz.getQuestion());

        addAnswersRB(quiz.getAnswer());
        currentQueNo++;
        Log.d(TAG, "showNewQuestion: Current ---> " + currentQueNo);
        if (quiz.getQuestionNumber() == totalQueNo) {
            btnNext.setText(R.string.finish);
        }
    }

    public void addAnswersRB(List<String> ans) {
        answerRadioGroup.removeAllViews();
        for (int row = 0; row < 1; row++) {
            answerRadioGroup.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < ans.size(); i++) {
                RadioButton rdbtn = new RadioButton(QuizActivity.this);
                rdbtn.setId((row * 2) + i);
                rdbtn.setText(ans.get(i));
                rdbtn.setTextSize(16);
                answerRadioGroup.addView(rdbtn);
            }
        }
        answerRadioGroup.clearCheck();
    }
}
