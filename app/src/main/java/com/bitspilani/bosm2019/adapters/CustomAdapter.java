
package com.bitspilani.bosm2019.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosm2019.models.Fixture;
import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.models.PlaceBetModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Fixture> fixtures;
    private Context context;
    private String[] teams;
    private int betAmount;
    private int walletamount;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String matchId;

    public CustomAdapter(ArrayList<Fixture> fixtures, Context context) {
        this.fixtures=fixtures;
        this.context=context;
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

        holder.team1.setText(fixtures.get(position).getteam1());
        holder.team2.setText(fixtures.get(position).getteam2());
        holder.time.setText(fixtures.get(position).getTime());
        holder.venue.setText(fixtures.get(position).getvenue());
        holder.team11.setText(fixtures.get(position).getteam1());
        holder.team21.setText(fixtures.get(position).getteam2());
        holder.time1.setText(fixtures.get(position).getTime());
        holder.venue1.setText(fixtures.get(position).getvenue());
        holder.match_id.setText(fixtures.get(position).getMatchId());
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
        SharedPreferences sharedPreferences = context.getSharedPreferences("WalletAmount",Context.MODE_PRIVATE);;
        TextView match_id;

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


        }

        @Override
        public void onClick(View v) {
            if(v.getId()==simple.getId())
                buildDialog(new String[]{team1.getText().toString(),team2.getText().toString()});
            else
                buildDialog(new String[]{team1.getText().toString(),team2.getText().toString()});
        }

        void buildDialog(String[] teams){

            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("I will place my bet on")
                    .setCancelable(true)
                    .setSingleChoiceItems(teams, -1,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {

                                }
                            }).setPositiveButton("Yay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences sharedPreferences=context.getSharedPreferences("WalletAmount",Context.MODE_PRIVATE);
                    int walletbalance=sharedPreferences.getInt("total",1000);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(betAmount==0)betAmount=100;
                    editor.putInt("total",walletbalance-betAmount);
                    editor.apply();

                    SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    String userId = sp.getString("username","");
                    Map<String,Object> bet = new HashMap<>();
                    PlaceBetModel ob = new PlaceBetModel(betAmount,userId,"BITS");
                    bet.put("0",ob);

                    db.collection("matches").document(match_id.getText().toString()).set(bet, SetOptions.merge()).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context,"Bet Placed",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            builder.show();
        }
    }
}


