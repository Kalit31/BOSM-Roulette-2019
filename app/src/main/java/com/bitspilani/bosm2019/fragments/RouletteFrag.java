package com.bitspilani.bosm2019.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitspilani.bosm2019.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public class RouletteFrag extends Fragment {

    // sectors of our wheel (look at the image to see the sectors)
    private static final String[] sectors = { "32 red", "15 black",
            "19 red", "4 black", "21 red", "2 black", "25 red", "17 black", "34 red",
            "6 black", "27 red","13 black", "36 red", "11 black", "30 red", "8 black",
            "23 red", "10 black", "5 red", "24 black", "16 red", "33 black",
            "1 red", "20 black", "14 red", "31 black", "9 red", "22 black",
            "18 red", "29 black", "7 red", "28 black", "12 red", "35 black",
            "3 red", "26 black", "zero"
    };
    private int total=0;
    private int count=0;
    private TextView YT;
    private String t1="0";
    private Button spinBtn;
    private ImageView wheel;

    // We create a Random instance to make our wheel spin randomly
    private static final Random RANDOM = new Random();
    private int degree = 0, degreeOld = 0;
    // We have 37 sectors on the wheel, we divide 360 by this value to have angle for each sector
    // we divide by 2 to have a half sector
    private static final float HALF_SECTOR = 360f / 37f / 2f;

    public RouletteFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v =  inflater.inflate(R.layout.fragment_roulette, container, false);
        ButterKnife.bind((Activity) getContext());
        YT = v.findViewById(R.id.score_val);
        YT.setText(t1);
        spinBtn = v.findViewById(R.id.spinBtn);
        wheel = v.findViewById(R.id.wheel);
        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spin(view);
            }
        });

       return v;
    }

    public void spin(View v) {
        spinBtn.setEnabled(false);

        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        spinBtn.setEnabled(true);
                    }
                });
            }
        }, 4000);
        count++;
        degreeOld = degree % 360;
        // we calculate random angle for rotation of our wheel
        degree = RANDOM.nextInt(360) + 720;
        // rotation effect on the center of the wheel
        if(count>=4){
            RotateAnimation rotateAnim = new RotateAnimation(degreeOld, 720,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(3600);
            rotateAnim.setFillAfter(true);
            rotateAnim.setInterpolator(new DecelerateInterpolator());
            rotateAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // we empty the result text view when the animation start
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // we display the correct sector pointed by the triangle at the end of the rotate animation
                    total=total+100;
                    count=0;
                    t1=Integer.toString(total);
                    YT.setText(t1);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            // we start the animation
            wheel.startAnimation(rotateAnim);
        }
        else{
            RotateAnimation rotateAnim = new RotateAnimation(degreeOld, degree,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(3600);
            rotateAnim.setFillAfter(true);
            rotateAnim.setInterpolator(new DecelerateInterpolator());
            rotateAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // we empty the result text view when the animation start
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // we display the correct sector pointed by the triangle at the end of the rotate animation
                    if(getSector(360 - (degree % 360)).charAt(2)==' ') {
                        if(getSector(360 - (degree % 360)).charAt(3)=='b')
                        {
                            total = total + Integer.parseInt(getSector(360 - (degree % 360)).substring(0, 2));
                            count=0;
                        }
                        else
                        {
                            total = total - Integer.parseInt(getSector(360 - (degree % 360)).substring(0, 2));
                            count++;
                        }
                    }
                    else if(getSector(360 - (degree % 360)).charAt(1)==' ') {
                        if(getSector(360 - (degree % 360)).charAt(2)=='b')
                        {
                            total=total+Integer.parseInt(getSector(360 - (degree % 360)).substring(0, 1));
                            count=0;
                        }
                        else
                        {
                            total = total - Integer.parseInt(getSector(360 - (degree % 360)).substring(0, 1));
                            count++;
                        }
                    }
                    else
                    {
                        total=total+100;
                        count=0;
                    }
                    t1=Integer.toString(total);
                    YT.setText(t1);
                    /*SharedPreferences sharedPreferences=getActivity().getSharedPreferences("WalletAmount", Context.MODE_PRIVATE);
                    int walletbalance=sharedPreferences.getInt("total",1000);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("total",walletbalance+total);
                    editor.apply();*/
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            // we start the animation
            wheel.startAnimation(rotateAnim);
        }
    }





    private String getSector(int degrees) {
        int i = 0;
        String text = null;

        do {
            // start and end of each sector on the wheel
            float start = HALF_SECTOR * (i * 2 + 1);
            float end = HALF_SECTOR * (i * 2 + 3);

            if (degrees >= start && degrees < end) {
                // degrees is in [start;end[
                // so text is equals to sectors[i];
                text = sectors[i];
            }

            i++;

        } while (text == null  &&  i < sectors.length);

        return text;
    }

}
