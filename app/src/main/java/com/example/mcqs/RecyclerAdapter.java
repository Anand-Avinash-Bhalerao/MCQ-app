package com.example.mcqs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.DrawableContainer;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<QuestionsData> list;
    private OnNoteClickListener onNoteClickListener;

    public RecyclerAdapter(Context context, List<QuestionsData> list,OnNoteClickListener onNoteClickListener) {
        this.context = context;
        this.onNoteClickListener = onNoteClickListener;
        this.list = list;
//        Toast.makeText(context, "Size of the list is "+list.size(), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder holder = new ViewHolder(view, onNoteClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        QuestionsData questionData = list.get(position);
        TextView question = holder.question;
        TextView score = holder.score;
        TextView time = holder.time;
        ImageView solved = holder.solved;
        LinearLayout linearLayout = holder.linearLayout;
        question.setText(String.valueOf("Question: "+(position+1)));
        score.setText(String.valueOf(questionData.getScore()));
        time.setText(String.valueOf(questionData.getTime()));
        solved.setImageResource(questionData.getBackground());
    }
    @Override
    public int getItemCount() {

//        Toast.makeText(context, "The item count is "+list.size(), Toast.LENGTH_SHORT).show();
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView question;
        private TextView score;
        private TextView time;
        private ImageView solved;
        private LinearLayout linearLayout;
        OnNoteClickListener onNoteClickListener;
        public ViewHolder(View itemView,OnNoteClickListener onNoteClickListener) {
            super(itemView);
            this.onNoteClickListener = onNoteClickListener;
            linearLayout = itemView.findViewById(R.id.back);
            solved = itemView.findViewById(R.id.background);
            question = itemView.findViewById(R.id.question);
            score = itemView.findViewById(R.id.score);
            time = itemView.findViewById(R.id.time);
            linearLayout = itemView.findViewById(R.id.back);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onNoteClickListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteClickListener{
        void onNoteClick(int pos);
    }


}

