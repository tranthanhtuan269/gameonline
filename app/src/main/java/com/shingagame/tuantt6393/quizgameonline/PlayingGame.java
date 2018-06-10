package com.shingagame.tuantt6393.quizgameonline;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shingagame.tuantt6393.quizgameonline.Common.Common;
import com.squareup.picasso.Picasso;

public class PlayingGame extends AppCompatActivity implements View.OnClickListener{

    final static long INTERVAL = 1000;
    final static long TIMEOUT = 8000;
    int progressValue = 0;

    CountDownTimer mCountDown;

    int index=0, score=0, thisQuestion=0, totalQuestion, correctAnswer;

//    FirebaseDatabase database;
//    DatabaseReference questions;

    ProgressBar progressBar;
    ImageView question_image;
    Button btnA, btnB, btnC, btnD;
    TextView txtScore, txtQuestionNum, question_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_game);

        totalQuestion = Common.questionList.size();

//        database = FirebaseDatabase.getInstance();
//        questions = database.getReference("Questions");

        txtScore = (TextView) findViewById(R.id.txtScore);
        txtQuestionNum = (TextView) findViewById(R.id.txtTotalQuestion);
        question_text = (TextView) findViewById(R.id.question_text);
        question_image = (ImageView)findViewById(R.id.question_image);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnA = (Button) findViewById(R.id.btnAnswerA);
        btnB = (Button) findViewById(R.id.btnAnswerB);
        btnC = (Button) findViewById(R.id.btnAnswerC);
        btnD = (Button) findViewById(R.id.btnAnswerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mCountDown.cancel();

        if(index < totalQuestion){
            Button clickedButton = (Button) view;
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())){
                score+= 1;
                correctAnswer++;
                showQuestion(++index);
            }else{
                Intent intent = new Intent(this, DoneActivity.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("SCORE", score);
                dataSend.putInt("TOTAL", totalQuestion);
                dataSend.putInt("CORRECT", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }
            txtScore.setText(String.format("%d", score));
        }
    }

    private void showQuestion(int i) {
        if(i < totalQuestion){
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d/%d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue=0;

            if(Common.questionList.get(index).getIsImageQuestion().equals("true")){
                question_text.setText(Common.questionList.get(i).getQuestion());
                Picasso.with(getBaseContext()).load(Common.questionList.get(i).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
            }else {
                question_text.setText(Common.questionList.get(i).getQuestion());
                question_image.setVisibility(View.INVISIBLE);
            }

            btnA.setText(Common.questionList.get(i).getAnswerA());
            btnB.setText(Common.questionList.get(i).getAnswerB());
            btnC.setText(Common.questionList.get(i).getAnswerC());
            btnD.setText(Common.questionList.get(i).getAnswerD());

            mCountDown.start();
        }else{
            Intent intent = new Intent(this, DoneActivity.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long l) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }
}
