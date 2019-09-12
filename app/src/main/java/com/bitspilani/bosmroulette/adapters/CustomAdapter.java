package com.bitspilani.bosmroulette.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_new,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapter.ViewHolder holder, int position) {

        holder.team1.setText(fixtures.get(position).getCollege1());
        holder.team2.setText(fixtures.get(position).getCollege2());
        holder.time.setText(fixtures.get(position).getTimestamp());
        holder.sports.setText(fixtures.get(position).getGame());
        //teams = new String[]{};



    }

    @Override
    public int getItemCount() {
        return fixtures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView team1,team2;
        TextView time,sports;
        String game;
        int count,total;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            team1=itemView.findViewById(R.id.team1);
            team2=itemView.findViewById(R.id.team2);

            time=itemView.findViewById(R.id.time);
            sports=itemView.findViewById(R.id.sports);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClicked(getAdapterPosition(), v);
        }


    }
    public interface ClickListener{
        void onItemClicked(int position,View v);
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        CustomAdapter.clickListener = clickListener;
    }
}