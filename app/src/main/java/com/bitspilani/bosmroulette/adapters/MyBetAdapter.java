package com.bitspilani.bosmroulette.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.models.UserBetModel;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyBetAdapter extends RecyclerView.Adapter<MyBetAdapter.ViewHolder>
{
    private ArrayList<UserBetModel> items = new ArrayList<>();
    private Context context;


    public MyBetAdapter(ArrayList<UserBetModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.mybet_layout,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.team1.setText(items.get(position).getTeam1());
        holder.team2.setText(String.valueOf(items.get(position).getTeam2()));

        if(items.get(position).getBettedOn()==0) {
            holder.team1.setTextColor(context.getResources().getColor(R.color.white));
            holder.team1.setBackground(context.getDrawable(R.drawable.borders_team_selected));
            holder.team2.setTextColor(context.getResources().getColor(R.color.black));
            holder.team2.setBackground(context.getDrawable(R.drawable.borders_team_notselected));
        }
        else {
            holder.team1.setTextColor(context.getResources().getColor(R.color.black));
            holder.team1.setBackground(context.getDrawable(R.drawable.borders_team_notselected));
            holder.team2.setTextColor(context.getResources().getColor(R.color.white));
            holder.team2.setBackground(context.getDrawable(R.drawable.borders_team_selected));
       }

        StringTokenizer strk = new StringTokenizer(String.valueOf(items.get(position).getBetAmount()),".");
        holder.betAmount.setText(strk.nextToken());
        holder.game.setText(items.get(position).getGame());
        if(!((items.get(position).getScore1() == -1) && (items.get(position).getScore2()==-1))) {
            holder.score1.setText(String.valueOf(items.get(position).getScore1()));
            holder.score2.setText(String.valueOf(items.get(position).getScore2()));
        }
        else{
            holder.score1.setText("");
            holder.score2.setText("");
        }
        if(items.get(position).isUpdate()){
            if(items.get(position).getBettedOn() == items.get(position).getResult()){
                holder.betAmount.setBackground(context.getDrawable(R.drawable.green_block));
            }else{
                holder.betAmount.setBackground(context.getDrawable(R.drawable.brown_block));
            }
        }else{
            holder.betAmount.setBackground(context.getDrawable(R.drawable.yellow_block));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView team1,team2;
        TextView betAmount,game,score1,score2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            team1 = itemView.findViewById(R.id.team1);
            team2 = itemView.findViewById(R.id.team2);
            betAmount = itemView.findViewById(R.id.bet_amount);
            game = itemView.findViewById(R.id.game);
            score1 = itemView.findViewById(R.id.score1);
            score2 = itemView.findViewById(R.id.score2);
        }
    }
}