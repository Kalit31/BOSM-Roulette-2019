package com.bitspilani.bosm2019.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitspilani.bosm2019.R;

public class ScoreActivity extends AppCompatActivity {


    private TextView tV_score;
    private Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tV_score = findViewById(R.id.score);
        close = findViewById(R.id.close_button);
        try{
            Typeface font = Typeface.createFromAsset(getAssets(),"fonts/helvetica.ttf");
            tV_score.setTypeface(font);
        }catch (Exception e)
        {
            Log.d("test",e.toString());
        }


            DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.4));

        int score = getIntent().getIntExtra("score",0);
        tV_score.setText("You won "+String.valueOf(score)+" points!");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



}
