package com.bitspilani.bosmroulette.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.models.FixtureModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<FixtureModel> fixtures;
    private Context context;
    private static ClickListener clickListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    String userId;


    public CustomAdapter(ArrayList<FixtureModel> fixtures, Context context) {
        this.fixtures = fixtures;
        this.context = context;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

        holder.team1.setText(fixtures.get(position).getCollege1());
        holder.team2.setText(fixtures.get(position).getCollege2());
        String time =  fixtures.get(position).getTimestamp();
        Date date =new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int hours = date.getHours();
        int minutes = date.getMinutes();
        String testMinutes = String.valueOf(minutes);
        if(testMinutes.length() == 1)
            testMinutes = testMinutes + "0";

        int dt = date.getDate();
        String month = " Sept";

        String t = dt + month + "\n"+ hours+" : "+ testMinutes;
        holder.time.setText(t);
        holder.sports.setText(fixtures.get(position).getGame());

        //teams = new String[]{};


    }

    @Override
    public int getItemCount() {
        return fixtures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView team1, team2;
        TextView time, sports;
        String game;
        int count, total;
   //     ImageView background;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            team1 = itemView.findViewById(R.id.team1);
            team2 = itemView.findViewById(R.id.team2);
       //     background = itemView.findViewById(R.id.sportsbg);
            time = itemView.findViewById(R.id.time);
            sports = itemView.findViewById(R.id.sports);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClicked(getAdapterPosition(), v);
        }


    }

    public interface ClickListener {
        void onItemClicked(int position, View v);
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        CustomAdapter.clickListener = clickListener;
    }
}