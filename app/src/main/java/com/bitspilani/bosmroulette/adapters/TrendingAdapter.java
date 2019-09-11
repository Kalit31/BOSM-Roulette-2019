package com.bitspilani.bosmroulette.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.models.Fixture;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TrendingAdapter extends FirestoreRecyclerAdapter<Fixture,TrendingAdapter.ViewHolder>

{

    private Context context;

    public TrendingAdapter(@NonNull FirestoreRecyclerOptions<Fixture> options, Context context) {
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
    protected void onBindViewHolder(@NonNull ViewHolder holder, int i, @NonNull Fixture model) {
        holder.name.setText(model.getCollege1());
        holder.rank.setText("1");
        holder.score.setText(String.valueOf(model.getCollege2()));
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
