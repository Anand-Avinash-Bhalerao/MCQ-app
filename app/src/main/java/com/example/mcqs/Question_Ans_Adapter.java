package com.example.mcqs;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Question_Ans_Adapter  extends RecyclerView.Adapter<Question_Ans_Adapter.ViewHolder>  {

    private Context context;
    private List<QuestionsData> list;

    public Question_Ans_Adapter(Context context, List<QuestionsData> list) {
        this.context = context;
        this.list = list;
//        Toast.makeText(context, "Size of the list is "+list.size(), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Question_Ans_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ans,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Question_Ans_Adapter.ViewHolder holder, int position) {
        QuestionsData questionData = list.get(position);
        TextView title = holder.title;
        TextView correct = holder.correct;
        TextView marked = holder.marked;
        ConstraintLayout constraintLayout = holder.constraintLayout;
        title.setText(String.valueOf(questionData.getQuestion()));
        marked.setText(String.valueOf(questionData.getMarked()));
        correct.setText(String.valueOf(questionData.getCorrect()));
//        constraintLayout.setBackground(ContextCompat.getColor(context,questionData.getCorrect_bg_color()));
        constraintLayout.setBackgroundResource(R.drawable.background_rounded);
//        constraintLayout.setBackgroundColor(questionData.getCorrect_bg_color());
        constraintLayout.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,questionData.getCorrect_bg_color())));
    }
    @Override
    public int getItemCount() {

//        Toast.makeText(context, "The item count is "+list.size(), Toast.LENGTH_SHORT).show();
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView correct;
        private TextView marked;
        private LinearLayout linearLayout;
        private ConstraintLayout constraintLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.back);
            title = itemView.findViewById(R.id.question_title);
            correct = itemView.findViewById(R.id.question_correct);
            marked = itemView.findViewById(R.id.question_marked);
        }
    }


}

