package com.bitspilani.bosm2019.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.models.MyBetsModel;
import com.bitspilani.bosm2019.models.UserBetModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyBetAdapter extends FirestoreRecyclerAdapter<UserBetModel,MyBetAdapter.ViewHolder>
{

    private Context context;

    public MyBetAdapter(@NonNull FirestoreRecyclerOptions<UserBetModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public MyBetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.mybet_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull UserBetModel model) {
      holder.event.setText(model.getTeam());
        Toast.makeText(context,model.getTeam(),Toast.LENGTH_LONG).show();
      holder.betAmount.setText(String.valueOf(model.getBetAmount()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView event,betAmount,betType;
        boolean completed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            event = itemView.findViewById(R.id.tV_event_name);
            betAmount = itemView.findViewById(R.id.tV_bet_amount);
            betType = itemView.findViewById(R.id.tV_betType);
        }
    }
}