package com.example.mcqs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mcqs.slider.SliderAdapter;
import com.example.mcqs.slider.SliderClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Handler sliderhandler = new Handler();
    LinearLayout dotsLayout;
    TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout test1 = findViewById(R.id.test1);
        LinearLayout test2 = findViewById(R.id.test2);
        LinearLayout test3 = findViewById(R.id.test3);

        dotsLayout = findViewById(R.id.indicator_container);
        dots = new TextView[6];
        dotsIndicator();

        test1.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("EXAM_TYPE","MATHS");
            startActivity(intent);
        });

        test2.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("EXAM_TYPE","STUPID");
            startActivity(intent);
        });
        test3.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("EXAM_TYPE","ANIME");
            startActivity(intent);
        });


        LinearLayout dev = findViewById(R.id.dev);
        dev.setOnClickListener(view -> {
            String url = "https://api.jsonbin.io/b/618a8121763da443125dfced/2";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        viewPager2 = findViewById(R.id.viewpager);
        List<SliderClass> sliderClasses = new ArrayList<>();
        sliderClasses.add(new SliderClass(R.drawable.slide_develop));
        sliderClasses.add(new SliderClass(R.drawable.slide_idea_pitch));
        sliderClasses.add(new SliderClass(R.drawable.slide_test));
        sliderClasses.add(new SliderClass(R.drawable.slide_idea));
        sliderClasses.add(new SliderClass(R.drawable.slide_code2));
        sliderClasses.add(new SliderClass(R.drawable.groupevents));
        viewPager2.setAdapter(new SliderAdapter(sliderClasses,viewPager2,this));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f+ r*0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectedIndicator(position);
                super.onPageSelected(position);

//                sliderhandler.postDelayed(sliderRunnable,5000);


            }
        });
    }

    private void selectedIndicator(int position) {
        for(int i =0;i<6;i++){
            if(i==position)
                dots[i].setTextColor(ContextCompat.getColor(this,R.color.cyan));
            else
                dots[i].setTextColor(ContextCompat.getColor(this,R.color.grey));
        }
    }

    private void dotsIndicator() {
        for(int i =0;i<6;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(18);
            dotsLayout.addView(dots[i]);
        }
    }



    // auto slide
//    private Runnable sliderRunnable = new Runnable() {
//        @Override
//        public void run() {
//            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
//        }
//    };
}