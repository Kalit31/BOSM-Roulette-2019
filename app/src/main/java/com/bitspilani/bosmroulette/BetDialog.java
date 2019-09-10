package com.bitspilani.bosmroulette;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.models.Fixture;
import com.bitspilani.bosmroulette.models.PlaceBetModel;
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
    TextView team1,team2,amt50,amt100,amt150,amt200;
    Button placebet;
    int betAmount=0,flag=-1,amtflag;
      double  walletamount;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    public BetDialog(@NonNull Context context, Fixture fixture,double walletamount) {
        super(context);
        this.context=context;
        this.fixture=fixture;
        this.walletamount=walletamount;
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
        placebet=findViewById(R.id.place_bet);
        amt50=findViewById(R.id.amt50);
        amt100=findViewById(R.id.amt100);
        amt150=findViewById(R.id.amt150);
        amt200=findViewById(R.id.amt200);
        team1.setText(fixture.getCollege1());
        team2.setText(fixture.getCollege2());
        team1.setOnClickListener(this);
        team2.setOnClickListener(this);
        placebet.setOnClickListener(this);
        amt50.setOnClickListener(this);
        amt100.setOnClickListener(this);
        amt150.setOnClickListener(this);
        amt200.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.team11:
                flag=0;
                team1.setBackgroundResource(R.drawable.amtselectedborder);
                team2.setBackgroundResource(R.drawable.amtborder);
                break;
            case R.id.team22:
                flag=1;
                team2.setBackgroundResource(R.drawable.amtselectedborder);
                team1.setBackgroundResource(R.drawable.amtborder);
                break;

            case R.id.place_bet:
                if((flag==0 || flag==1) && betAmount>0) {
                    placebet();
                    dismiss();
                }
                else if(walletamount-betAmount<=0)
                    Toast.makeText(context, "Not enough balance!!", Toast.LENGTH_SHORT).show();
                else if(betAmount==0)
                    Toast.makeText(context, "Select amount", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context,"Select a team",Toast.LENGTH_SHORT).show();
                break;
            case R.id.amt50:
                amt50.setBackgroundResource(R.drawable.amtselectedborder);
                amt50.setTextColor(Color.parseColor("#ffffff"));
                amt100.setBackgroundResource(R.drawable.amtborder);
                amt100.setTextColor(Color.parseColor("#000000"));
                amt150.setBackgroundResource(R.drawable.amtborder);
                amt150.setTextColor(Color.parseColor("#000000"));
                amt200.setBackgroundResource(R.drawable.amtborder);
                amt200.setTextColor(Color.parseColor("#000000"));
                amtflag=1;
                betAmount=50;
                break;

            case R.id.amt100:
                betAmount=100;
                amt100.setBackgroundResource(R.drawable.amtselectedborder);
                amt100.setTextColor(Color.parseColor("#ffffff"));
                amt50.setBackgroundResource(R.drawable.amtborder);
                amt50.setTextColor(Color.parseColor("#000000"));
                amt150.setBackgroundResource(R.drawable.amtborder);
                amt150.setTextColor(Color.parseColor("#000000"));
                amt200.setBackgroundResource(R.drawable.amtborder);
                amt200.setTextColor(Color.parseColor("#000000"));
                amtflag=1;
                break;

            case R.id.amt150:
                betAmount=150;
                amt150.setBackgroundResource(R.drawable.amtselectedborder);
                amt150.setTextColor(Color.parseColor("#ffffff"));
                amt50.setBackgroundResource(R.drawable.amtborder);
                amt50.setTextColor(Color.parseColor("#000000"));
                amt100.setBackgroundResource(R.drawable.amtborder);
                amt100.setTextColor(Color.parseColor("#000000"));
                amt200.setBackgroundResource(R.drawable.amtborder);
                amt200.setTextColor(Color.parseColor("#000000"));
                amtflag=1;
                break;

            case R.id.amt200:
                betAmount=200;
                amt200.setTextColor(Color.parseColor("#ffffff"));
                amt200.setBackgroundResource(R.drawable.amtselectedborder);
                amt50.setBackgroundResource(R.drawable.amtborder);
                amt50.setTextColor(Color.parseColor("#000000"));
                amt150.setBackgroundResource(R.drawable.amtborder);
                amt150.setTextColor(Color.parseColor("#000000"));
                amt100.setBackgroundResource(R.drawable.amtborder);
                amt100.setTextColor(Color.parseColor("#000000"));
                amtflag=1;
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
