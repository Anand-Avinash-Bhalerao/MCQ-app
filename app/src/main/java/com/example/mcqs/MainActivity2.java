package com.example.mcqs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button = findViewById(R.id.seeQuestions);
        CheckBox checkBox = findViewById(R.id.checkbox);
        String type = getIntent().getStringExtra("EXAM_TYPE");
        button.setOnClickListener(view -> {
            if(checkBox.isChecked()) {
                Intent intent = new Intent(this, Questions.class);
                intent.putExtra("EXAM_TYPE",type);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Checkbox is not selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity((new Intent(this,MainActivity.class)));
    }
}