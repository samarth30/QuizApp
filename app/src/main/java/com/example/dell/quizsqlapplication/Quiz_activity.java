package com.example.dell.quizsqlapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Quiz_activity extends AppCompatActivity {


    private TextView textViewQuestion,textviewscore,textViewQuestionCount,textViewCountdown;
    private RadioButton rb1,rb2,rb3;
    private RadioGroup rbGroup;

    private Button buttonConfirmNext;
    private List<Question> questionsList;

    private ColorStateList textColorDefaultRb;

    private int questioncounter,questionCountTotal;
    private int score;
    private boolean answered;
    private Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_activity);

        textViewQuestion = findViewById(R.id.text_view_question);
        textviewscore = findViewById(R.id.text_view_score);
        textViewCountdown = findViewById(R.id.text_view_countdown);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();


        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionsList = dbHelper.getAllQuestion();

        questionCountTotal = questionsList.size();
        Collections.shuffle(questionsList);

        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()){
                        checkAnswer();
                    }else{
                        Toast.makeText(Quiz_activity.this,"please select an answer",Toast.LENGTH_LONG).show();
                    }
                }else{
                    showNextQuestion();
                }
            }
        });

    }

    private void showNextQuestion() {

        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);

        rbGroup.clearCheck();

        if(questioncounter < questionCountTotal){
            currentQuestion = questionsList.get(questioncounter);

            textViewQuestion.setText(currentQuestion.getQuestion());

            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questioncounter++;
            textViewQuestionCount.setText("Question: " + questioncounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("confirm");


        }else{
            finishQuiz();
        }
    }

    private void finishQuiz() {

    }
    private void checkAnswer(){
        answered = true;


        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected)+1;

        if(answerNr == currentQuestion.getAnswerNr()){
            score++;
            textviewscore.setText("Score: "+ score);
        }
        showSolution();
    }

    private void showSolution() {

        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer one is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                break;
        }
        if(questioncounter<questionCountTotal){
            buttonConfirmNext.setText("Next");

        }else{
            buttonConfirmNext.setText("Finish");

        }
    }


}
