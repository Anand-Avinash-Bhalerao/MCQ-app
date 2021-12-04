package com.example.mcqs;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DisplayQues extends AppCompatActivity {
    public final String LOG_TAG = this.getClass().getName() + "Tag";
    TextView question, questionNo, timer;
    ImageView back;
    ConstraintLayout op1, op2, op3, op4;
    TextView op1_txt,op2_txt,op3_txt,op4_txt;
    ImageView op1_img,op2_img,op3_img,op4_img;
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    private int left = 0;
    int backPressed = 0;
    int notSolvedButNext = 0;
    int pos;
    private String correct;
    boolean minimized = false;
    boolean isSolved = false;
    boolean addedToScore;
    int score;
    List<QuestionsData> list;
    Button clear, next;
    int timeQuestionsAct;
    int currentTime;
    int timeToSolve;
    int toSubtract;
    QuestionsData temp;
    ImageView quesImg;
    String currentAns;
    boolean selected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.display_ques);
        setContentView(R.layout.activity_display_ques);
        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);
        op4 = findViewById(R.id.op4);

        question = findViewById(R.id.question_title);
        questionNo = findViewById(R.id.questionNo);
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progress);
        clear = findViewById(R.id.clear);
        next = findViewById(R.id.next);
        progressBar.setProgress(100);
        op1_txt = findViewById(R.id.op1_txt);
        op2_txt = findViewById(R.id.op2_txt);
        op3_txt = findViewById(R.id.op3_txt);
        op4_txt = findViewById(R.id.op4_txt);
        op1_img = findViewById(R.id.op1_img);
        op2_img = findViewById(R.id.op2_img);
        op3_img = findViewById(R.id.op3_img);
        op4_img = findViewById(R.id.op4_img);

        /*
        ye sab intents ka kaam hai.
        Questions.java me se idhar intent aata hai with:
        1. a List<QuestionsData> jisme are quesions hai
        2. Position of the question in the list
        3. Timer on the Questions activity. YE timer overall timer ka time hai.
         */
        Intent intent1 = getIntent();
        Intent intent = getIntent();
        list = new ArrayList<>((List<QuestionsData>) intent.getSerializableExtra("LIST"));
        pos = intent.getIntExtra("Index", -1);
        if (pos == -1) {
            pos = intent1.getIntExtra("POS", 0);
        }
        timeQuestionsAct = intent.getIntExtra("Time",0);
        Log.d("TimerTime", "The time from main act is : " + timeQuestionsAct);
        questionNo.setText(String.valueOf(pos + 1));
        TextView maxQues = findViewById(R.id.maxQuestions);
        maxQues.setText(String.valueOf("/" + list.size()));
        score = intent.getIntExtra("Score", 0);

        //temp me current question ka object rahega
        temp = list.get(pos);

        /*
        ye ab options ka List hai jisme sare options honge, fir vo shuffle coz random karna tha,
        Correct option ko alag se store kar k rakha hai
         */

        List<String> options = new ArrayList<>();
        options.add(temp.getOption1());
        options.add(temp.getOption2());
        options.add(temp.getOption3());
        options.add(temp.getCorrect());
        List<String> random = new ArrayList<>(options);
        Collections.shuffle(random);
        correct = temp.getCorrect();
        question.setText(temp.getQuestion());

        /*
        Check ki options me images hai ya text. dono sath me nahi honge.
        OPtions me ya toh sirf text ya toh sirf img
         */

        if(temp.getOptions_img_present()==0){
            op1_txt.setText(String.valueOf(random.get(0)));
            op2_txt.setText(String.valueOf(random.get(1)));
            op3_txt.setText(String.valueOf(random.get(2)));
            op4_txt.setText(String.valueOf(random.get(3)));
            op1_img.setVisibility(View.GONE);
            op2_img.setVisibility(View.GONE);
            op3_img.setVisibility(View.GONE);
            op4_img.setVisibility(View.GONE);
        }
        else{
            //text k andar uska value store karke rakha hai but unki visibility gone kar di
            //Submit k time yahi text view ki value se check karega fir
            op1_txt.setText(String.valueOf(random.get(0)));
            op2_txt.setText(String.valueOf(random.get(1)));
            op3_txt.setText(String.valueOf(random.get(2)));
            op4_txt.setText(String.valueOf(random.get(3)));
            op1_txt.setVisibility(View.GONE);
            op2_txt.setVisibility(View.GONE);
            op3_txt.setVisibility(View.GONE);
            op4_txt.setVisibility(View.GONE);
            Glide.with(this).load(random.get(0)).into(op1_img);
            Glide.with(this).load(random.get(1)).into(op2_img);
            Glide.with(this).load(random.get(2)).into(op3_img);
            Glide.with(this).load(random.get(3)).into(op4_img);


        }

        timer = findViewById(R.id.timer);
        int time = temp.getTime();
        Log.d(LOG_TAG, "The time is " + time);
        countDownTimer = new CountDownTimer((long) time * 1000, 1000) {
            @Override
            public void onTick(long l) {
//                timer.setText(String.valueOf(l / 1000));
//                String val = String.format("%02d:%02d",
//                        TimeUnit.MILLISECONDS.toMinutes(l) -
//                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
//                        TimeUnit.MILLISECONDS.toSeconds(l) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                int val = (int) (l / 1000);
                currentTime++;
                timer.setText(String.valueOf(val));
                progress(val, time);
                if (l <= 5000) {
                    timer.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                }
                Log.d(LOG_TAG, "The remaining time is " + l / 1000);
            }

            @Override
            public void onFinish() {
                Toasty.error(DisplayQues.this, "Time over", Toast.LENGTH_SHORT,true).show();
                list.get(pos).setSolved(true);
                if (isSolved) {
                    list.get(pos).setBackground(temp.check);
                } else {
                    list.get(pos).setBackground(temp.wrong);
                }
                timeToSolve = list.get(pos).getTime();
                submit();
                send(score, list);
            }
        };
        countDownTimer.start();
        back.setOnClickListener(view -> {
            onBackPressed();
        });

        Log.d(LOG_TAG, "The correct ans is " + correct);
        Log.d(LOG_TAG, "The options are: " + options);
        Log.d(LOG_TAG, "The random options are: " + random);

        clear.setOnClickListener(view -> {
            selected = false;
            op1.setBackgroundResource(R.drawable.background_rounded);
            op2.setBackgroundResource(R.drawable.background_rounded);
            op3.setBackgroundResource(R.drawable.background_rounded);
            op4.setBackgroundResource(R.drawable.background_rounded);
            currentAns = "";
            list.get(pos).setMarked(currentAns);
        });

        op1.setOnClickListener(view -> {
            selected = true;
            op1.setBackgroundResource(R.drawable.background_rounded_green);
            op2.setBackgroundResource(R.drawable.background_rounded);
            op3.setBackgroundResource(R.drawable.background_rounded);
            op4.setBackgroundResource(R.drawable.background_rounded);
            currentAns = String.valueOf(op1_txt.getText());
            list.get(pos).setMarked(currentAns);

        });
        op2.setOnClickListener(view -> {
            selected = true;
            op2.setBackgroundResource(R.drawable.background_rounded_green);
            op1.setBackgroundResource(R.drawable.background_rounded);
            op3.setBackgroundResource(R.drawable.background_rounded);
            op4.setBackgroundResource(R.drawable.background_rounded);
            currentAns = String.valueOf(op2_txt.getText());
            list.get(pos).setMarked(currentAns);
        });
        op3.setOnClickListener(view -> {
            selected = true;
            op3.setBackgroundResource(R.drawable.background_rounded_green);
            op1.setBackgroundResource(R.drawable.background_rounded);
            op2.setBackgroundResource(R.drawable.background_rounded);
            op4.setBackgroundResource(R.drawable.background_rounded);
            currentAns = String.valueOf(op3_txt.getText());
            list.get(pos).setMarked(currentAns);
        });
        op4.setOnClickListener(view -> {
            selected = true;
            op4.setBackgroundResource(R.drawable.background_rounded_green);
            op2.setBackgroundResource(R.drawable.background_rounded);
            op3.setBackgroundResource(R.drawable.background_rounded);
            op1.setBackgroundResource(R.drawable.background_rounded);
            currentAns = String.valueOf(op4_txt.getText());
            list.get(pos).setMarked(currentAns);
        });



        next.setOnClickListener(view -> {
            submit();
            if (isSolved) {
                list.get(pos).setSolved(true);
                list.get(pos).setBackground(temp.check);
                if (pos < list.size() - 1) {
                    while (pos < list.size()) {
                        if (list.get(pos).isSolved())
                            pos++;
                        else break;
                    }
                    if (pos < list.size())
                        next(score, list, pos);
                    else
                        send(score, list);
                } else {
                    Toasty.success(this, "This was the last question.", Toast.LENGTH_SHORT,true).show();
                    send(score, list);
                }
            } else {
                notSolvedButNext++;
                if (notSolvedButNext < 2) {
//                    Toast.makeText(this, "Not solved yet. Press again to go to next", Toast.LENGTH_SHORT).show();
                    Toasty.warning(this, "Not solved yet. Press again to go to next", Toast.LENGTH_SHORT, true).show();
                }

                else {
                    list.get(pos).setSolved(true);
                    list.get(pos).setBackground(temp.wrong);
                    if (pos < list.size() - 1) {
                        while (pos < list.size()) {
                            if (list.get(pos).isSolved())
                                pos++;
                            else break;
                        }
                        next(score, list, pos);
                    } else {
                        Toasty.success(this, "This was the last question.", Toast.LENGTH_SHORT,true).show();
                        send(score, list);
                    }
                }
            }
        });


        quesImg = findViewById(R.id.quesIMG);
        if(temp.getQues_img_present()==0)
            quesImg.setVisibility(View.GONE);
        else
        Glide.with(this)
                .load(temp.getQuestions_img())
                .into(quesImg);
}
    public void submit(){
        if(selected){
            timeToSolve = currentTime;
            Log.d("TimerTime", "Time to solve is " + timeToSolve);
//            Toast.makeText(this, "Answer recorded", Toast.LENGTH_SHORT).show();
            isSolved = true;
            boolean isCorrect = currentAns.equals(correct);
            if(isCorrect){
                score+=temp.getScore();
                list.get(pos).setCorrectOrNot(true);
            }
//            Toast.makeText(this, "The score is " + score, Toast.LENGTH_SHORT).show();
            send(score, list);
        }
//        else{
//            Toasty.warning(this, "No option Selected", Toast.LENGTH_SHORT,true).show();
//        }

//        int selected = radioGroup.getCheckedRadioButtonId();
//        if (radioGroup.getCheckedRadioButtonId() != -1) {
//            timeToSolve = currentTime;
//            Log.d("TimerTime", "Time to solve is " + timeToSolve);
////            Toast.makeText(this, "Answer recorded", Toast.LENGTH_SHORT).show();
//            isSolved = true;
//            RadioButton select = findViewById(selected);
//            String text = select.getText().toString();
//            boolean isCorrect = text.equals(correct);
////                Toast.makeText(this, "The ans is " + text.equals(correct), Toast.LENGTH_SHORT).show();
//            Log.d(LOG_TAG, "The selected option is : " + text);
//            Log.d(LOG_TAG, "The ans is : " + text.equals(correct));
//            if (isCorrect) {
//                if (!addedToScore) {
//                    score += temp.getScore();
//                    addedToScore = true;
//                    list.get(pos).setCorrectOrNot(true);
//                }
//            } else {
//                if (addedToScore)
//                    score -= temp.getScore();
//            }
////                Toast.makeText(this, "The score is "+score, Toast.LENGTH_SHORT).show();
////                send(score,list);
//        } else {
//            Log.d(LOG_TAG, "No option selected");
//            Toast.makeText(this, "No button selected", Toast.LENGTH_SHORT).show();
//        }
    }
    public void progress(int left, int total) {
        int p = (total - left) * 100 / total;
        progressBar.setProgress(p);
    }


    public void next(int score, List<QuestionsData> list, int pos) {
        Intent intent1 = new Intent(this, DisplayQues.class);
        intent1.putExtra("Score", score);
        intent1.putExtra("LIST", (Serializable) list);
        intent1.putExtra("POS", pos);
        timeSpent();
        intent1.putExtra("Time", timeQuestionsAct);
        startActivity(intent1);
    }

    public void send(int score, List<QuestionsData> list) {
        Intent intent1 = new Intent(this, Questions.class);
        intent1.putExtra("SCORE", score);
        intent1.putExtra("LIST", (Serializable) list);
        timeSpent();
        intent1.putExtra("Time", timeQuestionsAct);
        startActivity(intent1);
    }

    public void timeSpent(){
        timeQuestionsAct -=timeToSolve;
        Log.d("TimerTime", "The new time is " + timeQuestionsAct);
    }

    @Override
    public void onBackPressed() {
        submit();
        backPressed++;
        if (isSolved) {
            list.get(pos).setSolved(true);
            list.get(pos).setBackground(new QuestionsData().check);
            send(score, list);
        } else {
            if (backPressed <= 1) {
                Toasty.warning(this, "The current question will not be considered if you go back. Press again to go back", Toast.LENGTH_LONG,true).show();
            } else {
                list.get(pos).setSolved(true);
                list.get(pos).setBackground(new QuestionsData().wrong);
                timeToSolve = currentTime;
                send(score, list);

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
        minimized = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (minimized) {
            list.get(pos).setSolved(true);
            list.get(pos).setBackground(new QuestionsData().wrong);
            timeToSolve = currentTime;
            send(score, list);
        }
    }
}