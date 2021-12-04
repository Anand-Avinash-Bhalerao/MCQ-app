package com.example.mcqs.slider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mcqs.Home_page_Additional_info;
import com.example.mcqs.MainActivity;
import com.example.mcqs.R;
import com.example.mcqs.slider.SliderAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewholder>{
private List<SliderClass> sliderClasses;
private ViewPager2 viewPager2;
Context context;
    public SliderAdapter(List<SliderClass> sliderClasses, ViewPager2 viewPager2,Context context) {
        this.sliderClasses = sliderClasses;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewholder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewholder holder, int position) {
        holder.setImage(sliderClasses.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent additional = new Intent(context,Home_page_Additional_info.class);
            additional.putExtra("POSITION",position);
            context.startActivity(additional);
        });
//        if(position == sliderClasses.size() - 2){
////            viewPager2.post(runnable);
//        }
    }

    @Override
    public int getItemCount() {
        return sliderClasses.size();
    }

    class SliderViewholder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        SliderViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
        void setImage(SliderClass sliderClass){
            imageView.setImageResource(sliderClass.getImage());
        }
    }
//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            sliderClasses.addAll(sliderClasses);
//            notifyDataSetChanged();
//        }
//    };
}
