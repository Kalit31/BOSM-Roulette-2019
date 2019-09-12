package com.bitspilani.bosmroulette.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.models.TrendingModel;
import com.bitspilani.bosmroulette.models.TrendingModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TrendingAdapter extends FirestoreRecyclerAdapter<TrendingModel,TrendingAdapter.ViewHolder>

{

    private Context context;

    public TrendingAdapter(@NonNull FirestoreRecyclerOptions<TrendingModel> options, Context context) {
        super(options);
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.trending_layout,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int i, @NonNull TrendingModel model) {
        holder.team1.setText(model.getCollege1());
        holder.team2.setText(model.getCollege2());
        holder.sports.setText(model.getGame());
        if(!model.getScore1().equals("-1"))
                holder.score1.setText(model.getScore1());
        else
            holder.score1.setText("_");

        if(!model.getScore2().equals("-1"))
            holder.score2.setText(model.getScore2());
        else
            holder.score2.setText("_");
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sports,team1,team2,score1,score2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sports = itemView.findViewById(R.id.sports);
            team1 = itemView.findViewById(R.id.team1);
            team2 = itemView.findViewById(R.id.team2);
            score1 = itemView.findViewById(R.id.score1);
            score2  = itemView.findViewById(R.id.score2);
        }
    }
}
