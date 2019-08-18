
package com.bitspilani.bosm2019;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ArrayList<Fixture> fixtures;
    Context context;
    String[] teams;


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

        holder.Team1.setText(fixtures.get(position).getTeam1());
        holder.Team2.setText(fixtures.get(position).getTeam2());
        holder.Time.setText(fixtures.get(position).getTime());
        holder.Venue.setText(fixtures.get(position).getVenue());
        holder.Team11.setText(fixtures.get(position).getTeam1());
        holder.Team21.setText(fixtures.get(position).getTeam2());
        holder.Time1.setText(fixtures.get(position).getTime());
        holder.Venue1.setText(fixtures.get(position).getVenue());

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



        holder.amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.bet.setText(""+progress);

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
    public int getItemCount() {
        return fixtures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView Team1,Team2,Team11,Team21;
        TextView Time,Venue,Time1,Venue1;
        final FoldingCell fc;
        FrameLayout content,title;
        Button simple,power;
        SeekBar amount;
        TextView bet;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            Team1=itemView.findViewById(R.id.team1);
            Team2=itemView.findViewById(R.id.team2);
            Team11=itemView.findViewById(R.id.team11);
            Team21=itemView.findViewById(R.id.team21);
            Time=itemView.findViewById(R.id.time);
            Venue=itemView.findViewById(R.id.venue);
            Time1=itemView.findViewById(R.id.time1);
            Venue1=itemView.findViewById(R.id.venue1);
            fc=itemView.findViewById(R.id.folding_cell);
            content=itemView.findViewById(R.id.cell_content_view);
            title=itemView.findViewById(R.id.cell_title_view);
            simple=itemView.findViewById(R.id.simple);
            power=itemView.findViewById(R.id.power);
            amount=itemView.findViewById(R.id.amount);
            bet=itemView.findViewById(R.id.bet);

            simple.setOnClickListener(this);
            power.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if(v.getId()==simple.getId())
                buildDialog(new String[]{Team1.getText().toString(),Team2.getText().toString()});
            else
                buildDialog(new String[]{Team1.getText().toString(),Team2.getText().toString()});
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

                }
            });
            builder.show();
        }


    }

}


