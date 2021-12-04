package com.example.mcqs;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class QuestionsData implements Serializable{

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String correct;
    private String marked = "";
    private boolean correctOrNot = false;
    private int score;
    private int time;
    private boolean solved = false;
    private String solvedText;
    public final int check = R.drawable.check;
    public final int notCheck = R.drawable.question;
    public final int wrong = R.drawable.wrong;
    private int background = notCheck;
    private int ques_img_present;
    private int options_img_present;
    private String questions_img;
    private int correct_bg_color;

    public int getCorrect_bg_color() {
        return correct_bg_color;
    }

    public void setCorrect_bg_color(int correct_bg_color) {
        this.correct_bg_color = correct_bg_color;
    }

    public String getMarked() {
        return marked;
    }

    public void setMarked(String marked) {
        this.marked = marked;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public int getQues_img_present() {
        return ques_img_present;
    }

    public void setQues_img_present(int ques_img_present) {
        this.ques_img_present = ques_img_present;
    }

    public int getOptions_img_present() {
        return options_img_present;
    }

    public void setOptions_img_present(int options_img_present) {
        this.options_img_present = options_img_present;
    }

    public String getQuestions_img() {
        return questions_img;
    }

    public void setQuestions_img(String questions_img) {
        this.questions_img = questions_img;
    }

    public boolean isCorrectOrNot() {
        return correctOrNot;
    }

    public void setCorrectOrNot(boolean correctOrNot) {
        this.correctOrNot = correctOrNot;
    }

    public String getSolvedText() {
        return solvedText;
    }

    public void setSolvedText(String solvedText) {
        this.solvedText = solvedText;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public final String SOLVED = "Solved";
    public final String NOT_SOLVED = "Not Solved";

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public QuestionsData(){
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
