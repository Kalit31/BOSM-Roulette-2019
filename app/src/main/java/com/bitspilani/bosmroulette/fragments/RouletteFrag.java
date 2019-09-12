package com.bitspilani.bosmroulette.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.activity.HomeActivity;
import com.bitspilani.bosmroulette.activity.ScoreActivity;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class RouletteFrag extends Fragment {
    private static final String[] sectors = {"100", "550",
            "extra", "200", "500", "150", "450", "loss", "250",
            "100", "extra", "200", "150", "500", "125", "250",
            "100", "200", "300", "150", "extra", "125",
            "100", "200", "100", "150", "450", "loss",
            "loss", "200", "350", "400", "750", "125",
            "100", "200", "150"
    };
    int total = 0;
    private Date d1;
    private Date d2;
    private Date d3;
    int count = 0;
    private ImageView wheel;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String t1 = "0";

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
    private TextView featureInfo;

    private ImageView bonus;
    private ImageView loss;

    public RouletteFrag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roulette, container, false);

        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        bonus = view.findViewById(R.id.flash);
        loss = view.findViewById(R.id.heart);
        wheel = view.findViewById(R.id.wheel);
        ButterKnife.bind((Activity) getContext());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //     featureInfo = view.findViewById(R.id.tV_feature);
        String currentTime = sdf.format(calendar.getTime());
        try {
            d1 = sdf.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        wheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spin(view);
                wheel.setEnabled(false);
                return true;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        db.collection("users").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Log.d("name", documentSnapshot.get("email").toString());

                if (documentSnapshot.get("bonusTime").toString().length() != 0) {
                    try {
                        d2 = sdf.parse(documentSnapshot.get("bonusTime").toString());

                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                    if (d2.getTime() >= d1.getTime()) {
                        bonus.setVisibility(View.INVISIBLE);
                        HashMap<String, Object> disablebonus = new HashMap<>();
                        disablebonus.put("bonus", false);
                        db.collection("users").document(userId).set(disablebonus, SetOptions.merge());
                    }
                }
                if (documentSnapshot.get("lossTime").toString().length() != 0) {
                    try {
                        d3 = sdf.parse(documentSnapshot.get("lossTime").toString());
                    } catch (ParseException ex) {

                    }
                    Log.d("current", d1.toString());
                    Log.d("loss", d3.toString());
                    if (d1.getTime() >= d3.getTime()) {
                        loss.setVisibility(View.INVISIBLE);
                        HashMap<String, Object> disableloss = new HashMap<>();
                        disableloss.put("loss", false);
                        db.collection("users").document(userId).set(disableloss, SetOptions.merge());
                    }
                }

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
            wheel.setClickable(false);
            wheel.setEnabled(false);
        } else {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            wheel.setClickable(true);
            wheel.setEnabled(true);
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
        wheel.setEnabled(false);
        if (mTimerRunning) {
            // gestureListener.gDetector=null;
            wheel.setClickable(false);
            wheel.setEnabled(false);
        } else {
            wheel.setClickable(true);
            wheel.setEnabled(true);
            startTimer();
        }

        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (getActivity() == null)
                    return;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wheel.setEnabled(true);
                    }
                });
            }
        }, 20000);

        count++;
        degreeOld = degree % 360;
        // we calculate random angle for rotation of our wheel
        degree = RANDOM.nextInt(360) + 720;

        RotateAnimation rotateAnim = new RotateAnimation(degreeOld, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(3600);
        rotateAnim.setFillAfter(true);
        rotateAnim.setInterpolator(new DecelerateInterpolator());
        rotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.HOUR_OF_DAY, 3);

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String currentTime = sdf.format(calendar.getTime());
                Log.d("mytime", currentTime);


                if (getSector(360 - (degree % 360)).charAt(0) == 'e') {
                    Map<String, Object> bonusMap = new HashMap<>();
                    bonusMap.put("bonus", true);
                    bonusMap.put("bonusTime", currentTime);
                    bonus.setVisibility(View.VISIBLE);
                    db.collection("users").document(userId).set(bonusMap, SetOptions.merge());
                    //         featureInfo.setText("Bonus Activated for 3 hours!!");
                } else if (getSector(360 - (degree % 360)).charAt(0) == 'l') {
                    Map<String, Object> lossMap = new HashMap<>();
                    lossMap.put("loss", true);
                    loss.setVisibility(View.VISIBLE);
                    lossMap.put("lossTime", currentTime);
                    db.collection("users").document(userId).set(lossMap, SetOptions.merge());
                    //       featureInfo.setText("Loss Forgiveness activated for 3 hours !!");
                } else {
                    total = Integer.parseInt(getSector(360 - (degree % 360)).substring(0, 3));
                    db.collection("users").whereEqualTo("email", mAuth.getCurrentUser().getEmail()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                        for (DocumentSnapshot document : documents) {
                                            userId = document.get("username").toString();
                                            Log.d("user", userId.toString());
                                            wallet = Double.parseDouble(document.get("wallet").toString());
                                        }
                                        wallet = wallet + total;
                                        Map<String, Object> myWallet = new HashMap<>();
                                        myWallet.put("wallet", wallet);
                                        db.collection("users").document(userId).set(myWallet, SetOptions.merge());
                                    }
                                }
                            });
                    Intent scoreIntent = new Intent(getContext(), ScoreActivity.class);
                    scoreIntent.putExtra("score", total);
                    startActivity(scoreIntent);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel.startAnimation(rotateAnim);

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
            float start = HALF_SECTOR * (i * 2 + 1);
            float end = HALF_SECTOR * (i * 2 + 3);

            if (degrees >= start && degrees < end) {
                text = sectors[i];
            }
            i++;
        } while (text == null && i < sectors.length);

        return text;
    }
}


