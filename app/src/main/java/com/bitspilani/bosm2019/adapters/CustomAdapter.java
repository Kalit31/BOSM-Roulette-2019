package com.bitspilani.bosm2019.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.bitspilani.bosm2019.BetDialog;
import com.bitspilani.bosm2019.models.Fixture;
import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.models.PlaceBetModel;
import com.bitspilani.bosm2019.models.UserBetModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.ramotion.foldingcell.FoldingCell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Fixture> fixtures;
    private Context context;
    private String[] teams;
    private int betAmount;
    private int walletamount;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private String matchId;
    String userId;


    public CustomAdapter(ArrayList<Fixture> fixtures, Context context) {
        this.fixtures=fixtures;
        this.context=context;
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
    }


    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.folding_cell,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapter.ViewHolder holder, int position) {

        holder.team1.setText(fixtures.get(position).getCollege1());
        holder.team2.setText(fixtures.get(position).getCollege2());
        holder.time.setText(fixtures.get(position).getTimestamp());
        holder.team11.setText(fixtures.get(position).getCollege1());
        holder.team21.setText(fixtures.get(position).getCollege2());
        holder.time1.setText(fixtures.get(position).getTimestamp());
        holder.match_id.setText(fixtures.get(position).getMatchId());
        holder.game = fixtures.get(position).getGame();
        //teams = new String[]{};
        holder.fc.initialize(30,500, Color.parseColor("#ffffff"),0);

        holder.fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fc.toggle(false);
                if(holder.fc.isUnfolded())
                    holder.content.setVisibility(View.GONE);
                else
                    holder.title.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return fixtures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView team1,team2,team11,team21;
        TextView time,venue,time1,venue1;
        final FoldingCell fc;
        FrameLayout content,title;
        Button simple,power;
        SeekBar amount;
        TextView bet;
        TextView match_id;
        String teamSelected;
        int teamSelect;
        String game;
        int count,total;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            team1=itemView.findViewById(R.id.team1);
            team2=itemView.findViewById(R.id.team2);
            team11=itemView.findViewById(R.id.team11);
            team21=itemView.findViewById(R.id.team21);
            time=itemView.findViewById(R.id.time);
            venue=itemView.findViewById(R.id.venue);
            time1=itemView.findViewById(R.id.time1);
            venue1=itemView.findViewById(R.id.venue1);
            fc=itemView.findViewById(R.id.folding_cell);
            content=itemView.findViewById(R.id.cell_content_view);
            title=itemView.findViewById(R.id.cell_title_view);
            simple=itemView.findViewById(R.id.simple);
            power=itemView.findViewById(R.id.power);
            amount=itemView.findViewById(R.id.amount);
            bet=itemView.findViewById(R.id.bet);
            match_id = itemView.findViewById(R.id.match_id);
            simple.setOnClickListener(this);
            power.setOnClickListener(this);


            amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    bet.setText(""+progress);
                    betAmount=progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            if(betAmount == 0)
                betAmount = 100;

        }

        @Override
        public void onClick(View v) {

            db.collection("users").document(userId).get().addOnSuccessListener(
                    new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            double wallet = Double.parseDouble(documentSnapshot.get("wallet").toString());
                             if(wallet - betAmount >=0)
                                 {
                                    // if(v.getId()==simple.getId())
                                      //   buildDialog(new String[]{team1.getText().toString(),team2.getText().toString()});
                                     //else
                                       //  buildDialog(new String[]{team1.getText().toString(),team2.getText().toString()});
                                     if(v.getId()==simple.getId()){
                                         BetDialog betDialog=new BetDialog(context,fixtures.get(0));
                                         betDialog.show();
                                         Window window=betDialog.getWindow();
                                         window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,400);
                                         window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                     }
                              }
                             else
                                 Toast.makeText(context, "Not enough balance!!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );



        }

        void buildDialog(String[] teams){

            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("I will place my bet on")
                    .setCancelable(true)
                    .setSingleChoiceItems(teams, -1,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    teamSelected = teams[i];
                                    teamSelect = i;
                                }
                            }).setPositiveButton("Yay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Map<String,Object> bet = new HashMap<>();
                    PlaceBetModel ob = new PlaceBetModel(betAmount,userId,"BITS");

                    db.collection("matches").document(match_id.getText().toString()).update(
                      "roulette" , FieldValue.arrayUnion(ob)
                    );


                    db.collection("matches").document(match_id.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {
                               DocumentSnapshot doc = task.getResult();
                               if(teamSelect == 0) {
                                   count = Integer.parseInt(doc.get("team1").toString());
                                   db.collection("matches").document(match_id.getText().toString()).update(
                                           "team1",count+1
                                           );
                               }
                               else {
                                   count = Integer.parseInt(doc.get("team2").toString());
                                   db.collection("matches").document(match_id.getText().toString()).update(
                                           "team2",count+1
                                   );
                               }
                               total= Integer.parseInt(doc.get("total").toString());
                                db.collection("matches").document(match_id.getText().toString()).update(
                                        "total",total+1
                                );

                            }
                        }
                    });


                    Toast.makeText(context,"Bet Placed",Toast.LENGTH_SHORT).show();

                    Map<String, Object> userBet = new HashMap<>();

                    userBet.put("betAmount", betAmount);
                    userBet.put("match_id", match_id.getText().toString());
                    userBet.put("team1", teams[0]);
                    userBet.put("team2", teams[1]);
                    userBet.put("bettedOn", teamSelect);
                    userBet.put("game",game);
                    userBet.put("score1",-1);
                    userBet.put("score2",-1);
                    userBet.put("update",false);
                    userBet.put("result",-1);

                    db.collection("users").document(userId).collection("bets").document(match_id.getText().toString()).set(userBet);

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
            });
            builder.show();

        }
    }
}