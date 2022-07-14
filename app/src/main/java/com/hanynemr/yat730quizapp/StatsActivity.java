package com.hanynemr.yat730quizapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    TextView statsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        statsText = findViewById(R.id.statsText);

        ArrayList<Byte> scoreList = (ArrayList<Byte>) getIntent().getSerializableExtra("scores");

//        statsText.setText(scoreList.toString());

        for (int i = 0; i < scoreList.size(); i++) {
            statsText.append("Turn " + (i + 1) + " you got " + scoreList.get(i) + "\n");
        }

    }
}