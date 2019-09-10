package com.bitspilani.bosmroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.bitspilani.bosmroulette.activity.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView cards=findViewById(R.id.cards);
    Context context;
    Animation rotate=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
    cards.startAnimation(rotate);
    rotate.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
startActivity(new Intent(getApplicationContext(),LoginActivity.class));
finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    });
    }
}
