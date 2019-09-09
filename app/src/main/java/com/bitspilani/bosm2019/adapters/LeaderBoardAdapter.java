package com.bitspilani.bosm2019.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.models.RankClass;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>
{

    private ArrayList<RankClass> items;
    private Context context;

    public LeaderBoardAdapter(ArrayList<RankClass> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.leaderboard_item_layout,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position == 0){
            holder.layout.setBackgroundResource(R.drawable.rank_one);
        }
        if(position == 1){
            holder.layout.setBackgroundResource(R.drawable.rank_two);
        }
        if(position == 2){
            holder.layout.setBackgroundResource(R.drawable.rank_three);
        }
        holder.name.setText(items.get(position).getUsername().toUpperCase());
        holder.rank.setText(String.valueOf(items.get(position).getRank()));
        holder.score.setText(String.valueOf(items.get(position).getWallet()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank,name,score;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.constraint_leaderboard_item);
            rank =itemView.findViewById(R.id.player_rank);
            name= itemView.findViewById(R.id.player_name);
            score = itemView.findViewById(R.id.player_score);
        }
    }
}