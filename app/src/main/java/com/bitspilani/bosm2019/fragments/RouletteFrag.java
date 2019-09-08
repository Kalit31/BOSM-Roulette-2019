package com.bitspilani.bosm2019.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
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
import android.widget.Toast;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.activity.ScoreActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

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
            "100", "200", "500", "150", "450", "125", "250",
            "100", "200", "200", "150", "500", "125", "250",
            "100", "200", "300", "150", "100", "125",
            "100", "200", "100", "150", "450", "125",
            "100", "200", "350", "150", "750", "125",
            "100", "200", "150"
    };
    int total = 0;
    int count = 0;
    private ImageView wheel;

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
    SwipeGestureListener gestureListener;


    public RouletteFrag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roulette, container, false);

        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        wheel = view.findViewById(R.id.wheel);
        ButterKnife.bind((Activity) getContext());
        gestureListener = new SwipeGestureListener(getActivity());
        wheel.setOnTouchListener(gestureListener);
        mAuth = FirebaseAuth.getInstance();

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
        } else {
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            wheel.setClickable(true);
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
            gestureListener.gDetector=null;
            wheel.setClickable(false);
        } else {
            wheel.setClickable(true);
            gestureListener.gDetector =new GestureDetector(getContext(),gestureListener);
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
        // rotation effect on the center of the wheel
//        if(count>=4){
//            RotateAnimation rotateAnim = new RotateAnimation(degreeOld, 720,
//            RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//            rotateAnim.setDuration(3600);
//            rotateAnim.setFillAfter(true);
//            rotateAnim.setInterpolator(new DecelerateInterpolator());
//            rotateAnim.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    // we empty the result text view when the animation start
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    // we display the correct sector pointed by the triangle at the end of the rotate animation
//                    total=total+100;
//                    count=0;
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//
//            // we start the animation
//            wheel.startAnimation(rotateAnim);
//        }
        if (1 == 1) {
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
//                    if(getSector(360 - (degree % 360)).charAt(2)==' ') {
//                          total = total + Integer.parseInt(getSector(360 - (degree % 360)).substring(0, 2));
//                            count=0;
//
//                    }
//                    else if(getSector(360 - (degree % 360)).charAt(1)==' ') {
//                           total=total+Integer.parseInt(getSector(360 - (degree % 360)).substring(0, 1));
//                            count=0;
//
//                    }
////                    else
//                    {
//                        total=total+2000;
//                        count=0;
//                    }

                        total = total + Integer.parseInt(getSector(360 - (degree % 360)).substring(0, 3));

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
        } while (text == null && i < sectors.length);

        return text;
    }

    class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener implements
            View.OnTouchListener {
        Context context;
        GestureDetector gDetector;
        static final int SWIPE_MIN_DISTANCE = 120;
        static final int SWIPE_MAX_OFF_PATH = 250;
        static final int SWIPE_THRESHOLD_VELOCITY = 200;

        public SwipeGestureListener() {
            super();
        }

        public SwipeGestureListener(Context context) {
            this(context, null);
        }

        public SwipeGestureListener(Context context, GestureDetector gDetector) {

            if (gDetector == null)
                gDetector = new GestureDetector(context, this);

            this.context = context;
            this.gDetector = gDetector;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
            spin(getView());
            return super.onFling(e1, e2, velocityX, velocityY);

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            spin(getView());
            // Toast.makeText(getContext(),"Hello",Toast.LENGTH_SHORT).show();
            return gDetector.onTouchEvent(event);
        }

        public GestureDetector getDetector() {
            return gDetector;
        }

    }


}


