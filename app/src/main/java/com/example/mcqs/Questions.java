package com.example.mcqs;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Questions extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<QuestionsData>>, RecyclerAdapter.OnNoteClickListener {
    public List<QuestionsData> questionsData;
    public final String LOG_TAG = this.getClass().getName() + "Tag";
    RecyclerView recyclerView;
//    TextView scoreTextView;
    Boolean dataPresent = false;
    public int listLength = -1;
    RecyclerAdapter recyclerAdapter;
    public int score = 0;
    Button submit;
    CountDownTimer countDownTimer;
    int time;
    public String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        View view1 = getSupportActionBar().getCustomView();
        ImageView back = view1.findViewById(R.id.back);
        back.setVisibility(View.GONE);
        TextView title = view1.findViewById(R.id.title);
        recyclerView = findViewById(R.id.questionListRecycler);
        back.setOnClickListener(view -> {
            startActivity((new Intent(this,MainActivity.class)));
        });
        Log.d(LOG_TAG + "IND", "Oncreate");
        Intent intent = getIntent();
        type = getIntent().getStringExtra("EXAM_TYPE");
        Log.d("TypeOfExam", "exam is of "+type);
        submit = view1.findViewById(R.id.submit_test);
//        scoreTextView = findViewById(R.id.score);
//        scoreTextView.setText("Score: "+score);
        List<QuestionsData> temp = (List<QuestionsData>) intent.getSerializableExtra("LIST");
        if (temp == null)
            Log.d(LOG_TAG + "IND", "The array is null");
        if (temp != null) {
            questionsData = new ArrayList<>(temp);
            Log.d(LOG_TAG + "IND", "The size is " + questionsData.size());
            setList(questionsData);
        }
        if (temp == null)
            getSupportLoaderManager().initLoader(1, null, this);
        time = intent.getIntExtra("Time",-1);
        Log.d("TimerTime","Main activity new time is "+time);
        if(time==-1){
            time = 600;
        }

//        Toast.makeText(this, "The score on the question page is " + score, Toast.LENGTH_SHORT).show();

        submit.setOnClickListener(view -> {
            countDownTimer.cancel();
            Intent submit = new Intent(this, Result.class);
            submit.putExtra("LIST", (Serializable) questionsData);
            Log.d("BeforeSend","The score is "+score);
            submit.putExtra("Score", score);
            startActivity(submit);
        });
        TextView timer = findViewById(R.id.timer);
        countDownTimer = new CountDownTimer((long) time * 1000+1000, 1000) {
            @Override
            public void onTick(long l) {
//                timer.setText(String.valueOf(l / 1000));
//                String val = String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(l) -
//                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
//                        TimeUnit.MILLISECONDS.toSeconds(l) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                int val = (int) (l / 1000);
                time = val;
                timer.setText(String.valueOf(val));
                if (l <= 5000) {
                    timer.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                }
                Log.d(LOG_TAG, "The remaining time is " + l / 1000);
            }

            @Override
            public void onFinish() {
                Toast.makeText(Questions.this, "Time over", Toast.LENGTH_SHORT).show();
                submit.callOnClick();
            }
        };
        countDownTimer.start();
    }

    @NonNull
    @Override
    public Loader<List<QuestionsData>> onCreateLoader(int id, @Nullable Bundle args) {
        return new QuestionsLoader(this,type);
    }

    public void setList(List<QuestionsData> list){
        recyclerAdapter = new RecyclerAdapter(this, list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<List<QuestionsData>> loader, List<QuestionsData> data) {
//        Toast.makeText(this, "Adding values to the list", Toast.LENGTH_SHORT).show();
        if (listLength == -1)
            listLength = data.size();
        questionsData = new ArrayList<>(data);
        Collections.shuffle(questionsData);
        for (int i = data.size() - 1; i > listLength - 1; i--) {
//            Log.d(LOG_TAG,i+" "+data.get(i).getQuestion());
            questionsData.remove(i);
            data.remove(i);
        }
        Log.d(LOG_TAG, "After the removal,\n");
        for (int i = 0; i < data.size(); i++) {
            Log.d(LOG_TAG, i + " " + data.get(i).getQuestion());
        }
        boolean allDone = true;
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).isSolved())
                allDone = false;
        }

        if (allDone) {
//            Toast.makeText(this, "All the questions are solved", Toast.LENGTH_SHORT).show();
            Toasty.success(this, "All the questions are solved", Toast.LENGTH_SHORT, true).show();

        }
        Log.d(LOG_TAG, "DataList length is " + data.size());
        Log.d(LOG_TAG, "Data var length is " + listLength);
//        recyclerAdapter = new RecyclerAdapter(this, questionsData, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(recyclerAdapter);
        onNoteClick(0);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<QuestionsData>> loader) {

    }

    @Override
    public void onNoteClick(int pos) {
        QuestionsData temp = questionsData.get(pos);

        if (!temp.isSolved()) {
            temp.setSolved(true);
            temp.setBackground(temp.check);
            questionsData.set(pos, temp);
            Intent intent = new Intent(this, DisplayQues.class);
            intent.putExtra("Item", temp);
            intent.putExtra("Index", pos);
            intent.putExtra("LIST", (Serializable) questionsData);
            intent.putExtra("Score", score);
            intent.putExtra("Time",time);
            startActivity(intent);
        } else {
            if(temp.getBackground()==temp.wrong){
//                Toast.makeText(this, "You ran out of time! :(", Toast.LENGTH_SHORT).show();
                Toasty.error(this, "You ran out of time! :(", Toast.LENGTH_SHORT, true).show();
            }
            else
//            Toast.makeText(this, "The question is already solved", Toast.LENGTH_SHORT).show();
            Toasty.info(this, "The question is already solved", Toast.LENGTH_SHORT, true).show();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataPresent = true;
//        Toast.makeText(this, "OnPause " + dataPresent, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this, "Resumed", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        score = intent.getIntExtra("SCORE",0);
        if (questionsData != null) {
            for (int i = 0; i < questionsData.size(); i++) {
                Log.d(LOG_TAG + "IND", "Ind: " + i + " is " + questionsData.get(i).isSolved());
            }
            setList(questionsData);
        } else {
            Log.d(LOG_TAG + "IND", "Null kyun hai bc!");
        }
    }

    @Override
    public void onBackPressed() {
//        startActivity(new Intent(this,MainActivity2.class));
        Toast.makeText(this, "progress will be lost", Toast.LENGTH_SHORT).show();
    }
}