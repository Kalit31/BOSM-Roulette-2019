package com.bitspilani.bosm2019.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.models.LeaderBoardModel;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>
{

    private ArrayList<LeaderBoardModel> items = new ArrayList<>();
    private Context context;

    public LeaderBoardAdapter(ArrayList<LeaderBoardModel> items, Context context) {
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
        holder.name.setText(items.get(position).getName());
        holder.rank.setText(items.get(position).getRank());
        holder.score.setText(items.get(position).getScore());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank,name,score;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank =itemView.findViewById(R.id.player_rank);
            name= itemView.findViewById(R.id.player_name);
            score = itemView.findViewById(R.id.player_score);
        }
    }
}
