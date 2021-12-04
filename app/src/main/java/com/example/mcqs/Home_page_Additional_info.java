package com.example.mcqs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mcqs.Additional_info_page.Info_class;

import org.w3c.dom.Text;

public class Home_page_Additional_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_additional_info);
        int pos = getIntent().getIntExtra("POSITION",0);
        Info_class[] array = new Info_class[6];
        array[0] = new Info_class("Development","2-3 PM","Build Apps and Websites","NA",R.drawable.app);
        array[1] = new Info_class("Idea Pitch","3-4 PM","Pitch ideas about a product in a group","Teams",R.drawable.ideass);
        array[2] = new Info_class("MCQ's","4-5 PM","Solve interesting questions. Compete against others","Here onlu :p",R.drawable.mcq);
        array[3] = new Info_class("IDEAs","5-6 PM","IDEAS 2.0","Teams",R.drawable.ideaa);
        array[4] = new Info_class("Competitive Coding","7-8PM","Solve challenging problems to gain good ranks","HackerRank",R.drawable.coding);
        array[5] = new Info_class("Group Events","8-9PM","Exciting events for groups","NA",R.drawable.group);

        Info_class current = array[pos];
        TextView title = findViewById(R.id.title);
        TextView time = findViewById(R.id.time);
        TextView info = findViewById(R.id.info);
        ImageView imageView = findViewById(R.id.image);
        title.setText(current.getTitle());
        time.setText("TIME: "+current.getTime());
        info.setText(current.getInfo());
        imageView.setImageResource(current.getImage());
    }
}