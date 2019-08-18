package com.bitspilani.bosm2019.fragments;

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

import java.util.ArrayList;

public class LeaderBoardFrag extends Fragment {

    private RecyclerView leaderlist;
    private LeaderBoardAdapter adapter;
    private ArrayList<LeaderBoardModel> items = new ArrayList<>();
    private TextView yourRank,yourScore;


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
         if(items.isEmpty()) {
             items.add(new LeaderBoardModel("1", "Player 1", "100"));
             items.add(new LeaderBoardModel("2", "Player 2", "80"));
             items.add(new LeaderBoardModel("3", "Player 3", "70"));
             items.add(new LeaderBoardModel("4", "Player 4", "60"));
             items.add(new LeaderBoardModel("5", "Player 5", "50"));
             items.add(new LeaderBoardModel("6", "Player 6", "45"));
             items.add(new LeaderBoardModel("7", "Player 7", "40"));
             items.add(new LeaderBoardModel("8", "Player 8", "30"));
         }
        adapter = new LeaderBoardAdapter(items,getContext());
        leaderlist.setLayoutManager(new LinearLayoutManager(getContext()));
        leaderlist.setHasFixedSize(true);
        leaderlist.setAdapter(adapter);
         return v;
    }

}
