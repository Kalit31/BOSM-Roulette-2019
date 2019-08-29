package com.bitspilani.bosm2019.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitspilani.bosm2019.adapters.LeaderBoardAdapter;
import com.bitspilani.bosm2019.models.LeaderBoardModel;
import com.bitspilani.bosm2019.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class LeaderBoardFrag extends Fragment {

    private RecyclerView leaderlist;
    private LeaderBoardAdapter adapter;
    private ArrayList<LeaderBoardModel> items = new ArrayList<>();
    private TextView yourRank,yourScore;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference leaderRef;
    private SharedPreferences sharedPreferences;

    public LeaderBoardFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_leader_board, container, false);
         leaderlist = v.findViewById(R.id.leader_rv);
         yourRank = v.findViewById(R.id.your_rank);
         yourScore = v.findViewById(R.id.your_score);
         yourScore.setText("70pts");
         yourRank.setText("3rd");
        sharedPreferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("username","");
        if (userId != null) {
            leaderRef = db.collection("users");
            setUpRecyclerView();
        }


         return v;
    }
    private void setUpRecyclerView() {
        Query query = leaderRef.orderBy("wallet", Query.Direction.DESCENDING).limit(10);
        FirestoreRecyclerOptions<LeaderBoardModel> options = new FirestoreRecyclerOptions.Builder<LeaderBoardModel>()
                .setQuery(query,LeaderBoardModel.class)
                .build();
        adapter = new LeaderBoardAdapter(options,getContext());
        leaderlist.setLayoutManager(new LinearLayoutManager(getContext()));
        leaderlist.setHasFixedSize(true);
        leaderlist.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
