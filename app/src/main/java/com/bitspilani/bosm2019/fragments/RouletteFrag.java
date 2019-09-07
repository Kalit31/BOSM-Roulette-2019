package com.bitspilani.bosm2019.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.RotationGestureDetector;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class RouletteFrag extends Fragment {
    private static final String[] sectors = { "32 red", "15 black",
            "19 red", "4 black", "21 red", "2 black", "25 red", "17 black", "34 red",
            "6 black", "27 red","13 black", "36 red", "11 black", "30 red", "8 black",
            "23 red", "10 black", "5 red", "24 black", "16 red", "33 black",
            "1 red", "20 black", "14 red", "31 black", "9 red", "22 black",
            "18 red", "29 black", "7 red", "28 black", "12 red", "35 black",
            "3 red", "26 black", "zero"
    };
    int total=0;
    int count=0;
    private ImageView wheel;
    private TextView YT;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  String t1="0";
    private Button spinbtn;
    private static final long START_TIME_IN_MILLIS = 20000;
    private FirebaseAuth mAuth;
    private String userId;
    private TextView mTextViewCountDown;
    private double wallet;
    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis;
    private long mEndTime;
    private static final Random RANDOM = new Random();
    private int degree = 0, degreeOld = 0;
    private static final float HALF_SECTOR = 360f / 37f / 2f;


    public RouletteFrag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roulette, container, false);
        YT = view.findViewById(R.id.score_val);
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        wheel = view.findViewById(R.id.wheel);
        ButterKnife.bind((Activity) getContext());
        spinbtn = view.findViewById(R.id.spinBtn);
        mAuth= FirebaseAuth.getInstance();
        YT.setText(t1);
        spinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin(v);
            }
        });
        return view;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (mTimerRunning) {
            spinbtn.setClickable(false);
        } else {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            spinbtn.setClickable(true);
        }
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }


    public void spin(View v) {
        spinbtn.setEnabled(false);
        if(mTimerRunning){
            spinbtn.setClickable(false);
        }else{
            spinbtn.setClickable(true);
            startTimer();
        }

        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                if(getActivity()==null)
                    return;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinbtn.setEnabled(true);
                    }
                });
            }
        }, 20000);

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
        else
        {
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
                    db.collection("users").whereEqualTo("email",mAuth.getCurrentUser().getEmail()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                        for(DocumentSnapshot document: documents){
                                            userId = document.get("username").toString();
                                            Log.d("user",userId.toString());
                                            wallet = Double.parseDouble(document.get("wallet").toString());
                                        }
                                        wallet= wallet + total;
                                        Map<String,Object> myWallet = new HashMap<>();
                                        myWallet.put("wallet",wallet);
                                        db.collection("users").document(userId).set(myWallet, SetOptions.merge());
                                    }
                                }
                            });
                    YT.setText(t1);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            // we start the animation
            wheel.startAnimation(rotateAnim);
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = getContext().getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateButtons();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();
            }
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