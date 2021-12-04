package com.example.mcqs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skydoves.progressview.ProgressView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.bastanfar.semicirclearcprogressbar.SemiCircleArcProgressBar;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        List<QuestionsData> questionList = (List<QuestionsData>) intent.getSerializableExtra("LIST");
        int score = intent.getIntExtra("Score", -1);
        TextView scoreText = findViewById(R.id.score);
        ProgressView progressView = findViewById(R.id.progressBar);
        SemiCircleArcProgressBar scoreProgress = findViewById(R.id.score_progressBar);
        StringBuilder val = new StringBuilder();
        int actual = 0;
//        Toast.makeText(this, "Firebase se end me connect karna hai bus", Toast.LENGTH_SHORT).show();
        int solved = 0;
        int totalScore = 0;
        for (int i = 0; i < questionList.size(); i++) {
            totalScore+=questionList.get(i).getScore();
            if (questionList.get(i).isSolved())
                solved++;
            Log.d("ResultTag", (i + 1) + " is: " + questionList.get(i).isCorrectOrNot());
            String x = (i + 1) + " is: " + questionList.get(i).isCorrectOrNot() + "\n";
            actual += questionList.get(i).getScore();
            val.append(x);
            Log.d("CORRECT_HAI",questionList.get(i).getSolvedText()+" "+questionList.get(i).getCorrect());
            if(questionList.get(i).getMarked().equals(questionList.get(i).getCorrect())){
                questionList.get(i).setCorrect_bg_color(R.color.lighter_green);
            }
            else{
                questionList.get(i).setCorrect_bg_color(R.color.lighter_red);
            }
            if(questionList.get(i).getMarked().equals("")){
                questionList.get(i).setMarked("Not solved");
            }
        }


        TextView noOfSolved = findViewById(R.id.noOfAttempted);
        TextView noOfTotal = findViewById(R.id.noOfTotal);
        noOfSolved.setText(String.valueOf(solved));
        noOfTotal.setText(String.valueOf("/" + questionList.size()));
        scoreText.setText(String.valueOf(score));
        int progressVar = (int)(((float)solved/(float)questionList.size())*100);
        progressView.setProgress(progressVar);
        progressView.setLabelText(progressVar+"%");
//        Toast.makeText(this, "questionList is "+progressVar, Toast.LENGTH_SHORT).show();

        int scoreProgVal = (int)(((float)score/(float)totalScore)*100);
//        Toast.makeText(this, "val is "+scoreProgVal, Toast.LENGTH_SHORT).show();
        scoreProgress.setPercent(scoreProgVal);

        RecyclerView recyclerView = findViewById(R.id.question_ans_recycler);
        Question_Ans_Adapter question_ans_adapter = new Question_Ans_Adapter(this,questionList);
        recyclerView.setAdapter(question_ans_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        final Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//
//            }
//        }, 1000);
    }

    public void home(View view) {
        startActivity((new Intent(this, MainActivity.class)));
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "The test is submitted", Toast.LENGTH_SHORT).show();
    }
}