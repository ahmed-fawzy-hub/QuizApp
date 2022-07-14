package com.hanynemr.yat730quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    TextView tvQuestion, questionNumber, lastText;
    Button startButton, answerButton, scoreButton;
    Spinner spnAnswer;

    String[] countries = {"egypt", "usa", "france", "uk"};
    String[] cities = {"cairo", "ws", "paris", "london"};
    byte score, i;

    MediaPlayer player;

//    ArrayList<Integer> indices=new ArrayList<>();

    ArrayList<Byte> scoreList = new ArrayList<>();

    ArrayList<String> items = new ArrayList<>();

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvQuestion = findViewById(R.id.tvQuestion);
        spnAnswer = findViewById(R.id.spnAnswer);
        startButton = findViewById(R.id.startButton);
        answerButton = findViewById(R.id.answerButton);
        scoreButton = findViewById(R.id.scoreButton);
        questionNumber = findViewById(R.id.questionNumber);
        lastText = findViewById(R.id.lastText);

        pref = getPreferences(MODE_PRIVATE);
        int lastScore = pref.getInt("score", -1);
        if (lastScore > -1)
            lastText.setText("your last score is " + lastScore);


    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("score", score);
        editor.apply();
        super.onBackPressed();
    }

    public void start(View view) {
        tvQuestion.setText("what is the capital of " + countries[0]);
        answerButton.setEnabled(true);
        spnAnswer.setEnabled(true);
        i = 0;
        score = 0;

        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }

        questionNumber.setText("Question " + (i + 1) + " of " + countries.length);

        //step1 fill items
        items.clear();
        Collections.addAll(items, "please select",
                "cairo",
                "tokio",
                "bejing",
                "ws",
                "baghdad",
                "khartoum",
                "london",
                "toronto",
                "paris");

        //step 2 bind items on spinner
        //spinner->adapter->items
        //fill items, pass items to adapter,pass adapter to spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        spnAnswer.setAdapter(adapter);

//        indices.clear();

    }

    public void answer(View view) {
        String answer = spnAnswer.getSelectedItem().toString();

        if (answer.equals("please select")) {
            Toast.makeText(this, "please select a valid answer", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (indices.contains(spnAnswer.getSelectedItemPosition())){
//            Toast.makeText(this, "selected before", Toast.LENGTH_SHORT).show();
//            return;
//        }


        if (answer.equals(cities[i])) {
            score++;
            items.remove(answer);
//            indices.add(spnAnswer.getSelectedItemPosition());
        }

        i++;
        if (i < countries.length) {
            tvQuestion.setText("what is the capital of " + countries[i]);
            questionNumber.setText("Question " + (i + 1) + " of " + countries.length);
        } else {
            scoreButton.setEnabled(true);
            answerButton.setEnabled(false);
            scoreList.add(score);
            Toast.makeText(this, "score is " + score + " and you got it "
                            + Collections.frequency(scoreList, score) + " times"
                    , Toast.LENGTH_LONG).show();
            if (score >= 3) {
                player = MediaPlayer.create(this, R.raw.success);
                player.start();
            } else {
                player = MediaPlayer.create(this, R.raw.fail);
                player.start();
            }
        }


        spnAnswer.setSelection(0);

//        Collections.shuffle(items);
//        int index=items.indexOf("please select"); // O(n)
//        Collections.swap(items,0,index); O(1)

        Collections.shuffle(items.subList(1, items.size()));


//        items.remove("please select"); // O(n)
//        Collections.shuffle(items);
//        items.add(0,"please select"); // O(n)


    }

    public void high(View view) {
        if (!scoreList.isEmpty()) {
            byte max = Collections.max(scoreList);
            Toast.makeText(this, "Highest score is " + max, Toast.LENGTH_SHORT).show();
        }

    }

    public void stats(View view) {

        Intent in = new Intent(this, StatsActivity.class);
        in.putExtra("scores", scoreList); // serializable
        startActivity(in);

    }
}