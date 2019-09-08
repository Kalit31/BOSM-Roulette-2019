package com.bitspilani.bosm2019;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.models.Fixture;
import com.bitspilani.bosm2019.models.PlaceBetModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BetDialog extends Dialog implements View.OnClickListener {
    String userId;
    Fixture fixture;
    Context context;
    TextView team1,team2,amt;
    SeekBar amountplaced;
    Button placebet;
    int betAmount=100,flag=-1;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    public BetDialog(@NonNull Context context, Fixture fixture) {
        super(context);
        this.context=context;
        this.fixture=fixture;
        mAuth = FirebaseAuth.getInstance();
        db.collection("users").whereEqualTo("email",mAuth.getCurrentUser().getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            for(DocumentSnapshot document: documents){
                                userId = document.get("username").toString();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bet_dialog);
        team1=findViewById(R.id.team11);
        team2=findViewById(R.id.team22);
        amountplaced=findViewById(R.id.seekBar);
        placebet=findViewById(R.id.place_bet);
        amt=findViewById(R.id.textamt);
        team1.setText(fixture.getCollege1());
        team2.setText(fixture.getCollege2());
        team1.setOnClickListener(this);
        team2.setOnClickListener(this);
        placebet.setOnClickListener(this);

        amountplaced.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amt.setText(String.valueOf(progress));
                betAmount=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.team11:
                flag=0;
                team1.setBackgroundResource(R.drawable.yellow_bordered);
                team2.setBackgroundResource(R.drawable.leaderboard_border);
                break;
            case R.id.team22:
                flag=1;
                team2.setBackgroundResource(R.drawable.yellow_bordered);
                team1.setBackgroundResource(R.drawable.leaderboard_border);
                break;

            case R.id.place_bet:
                if(flag==0 || flag==1) {
                    placebet();
                    dismiss();
                }
                else
                    Toast.makeText(context,"Select a team",Toast.LENGTH_SHORT).show();
                break;

            default:break;
        }

    }
    void placebet(){

        Map<String,Object> bet = new HashMap<>();
        PlaceBetModel ob = new PlaceBetModel(betAmount,userId,"BITS");

        db.collection("matches").document(fixture.getMatchId().toString()).update(
                "roulette" , FieldValue.arrayUnion(ob)
        );
        Toast.makeText(context,"Bet Placed",Toast.LENGTH_SHORT).show();

        Map<String, Object> userBet = new HashMap<>();

        userBet.put("betAmount", betAmount);
        userBet.put("match_id", fixture.getMatchId());
        userBet.put("team1", fixture.getCollege1());
        userBet.put("team2", fixture.getCollege2());
        userBet.put("bettedOn",flag);
        userBet.put("game",fixture.getGame());
        userBet.put("score1",-1);
        userBet.put("score2",-1);
        userBet.put("update",false);
        userBet.put("result",-1);

        db.collection("users").document(userId).collection("bets").document(fixture.getMatchId().toString()).set(userBet);

        db.collection("users").document(userId).get().addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        double wallet = Double.parseDouble(documentSnapshot.get("wallet").toString());
                        wallet = wallet - betAmount;
                        Map<String, Double> walletMap = new HashMap<>();
                        walletMap.put("wallet", wallet);

                        db.collection("users").document(userId)
                                .set(walletMap, SetOptions.merge());
                    }
                }
        );
    }
}
