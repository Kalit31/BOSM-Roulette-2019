package com.bitspilani.bosmroulette.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosmroulette.models.Fixture;
import com.bitspilani.bosmroulette.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Fixture> fixtures;
    private Context context;
    private static ClickListener clickListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapter.ViewHolder holder, int position) {

        holder.team1.setText(fixtures.get(position).getCollege1());
        holder.team2.setText(fixtures.get(position).getCollege2());
        holder.time.setText(fixtures.get(position).getTimestamp());
        holder.game = fixtures.get(position).getGame();
        //teams = new String[]{};



    }

    @Override
    public int getItemCount() {
        return fixtures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView team1,team2;
        TextView time;
        String game;
        int count,total;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            team1=itemView.findViewById(R.id.team1);
            team2=itemView.findViewById(R.id.team2);

            time=itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClicked(getAdapterPosition(), v);
        }

        /*@Override
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
<<<<<<< Updated upstream
                                         window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,400);
=======
                                         window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,600);
>>>>>>> Stashed changes
                                         window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                     }
                              }
                             else
                                 Toast.makeText(context, "Not enough balance!!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );



        }*/

        /*void buildDialog(String[] teams){

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

        }*/
    }
    public interface ClickListener{
        void onItemClicked(int position,View v);
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        CustomAdapter.clickListener = clickListener;
    }
}