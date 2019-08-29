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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class LeaderBoardAdapter extends FirestoreRecyclerAdapter<LeaderBoardModel,LeaderBoardAdapter.ViewHolder>
        // RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>
{

    private Context context;

    public LeaderBoardAdapter(@NonNull FirestoreRecyclerOptions<LeaderBoardModel> options, Context context) {
        super(options);
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.leaderboard_item_layout,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int i, @NonNull LeaderBoardModel leaderBoardModel) {
        holder.name.setText(leaderBoardModel.getName());
        holder.rank.setText("1");
        holder.score.setText(String.valueOf(leaderBoardModel.getWallet()));
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
