package com.bitspilani.bosm2019.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.adapters.MyBetAdapter;
import com.bitspilani.bosm2019.models.MyBetsModel;

import java.util.ArrayList;

public class MyBetsFrag extends Fragment {

    private RecyclerView betlist;
    private MyBetAdapter adapter;
    private ArrayList<MyBetsModel> items = new ArrayList<>();

    public MyBetsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_my_bets, container, false);
        betlist = v.findViewById(R.id.myBets_rv);
        items.add(new MyBetsModel("TITS vs MNITS",200,"Bet type",false,true));
        items.add(new MyBetsModel("TITS vs MNITS",200,"Bet type",false,false));
        items.add(new MyBetsModel("TITS vs MNITS",200,"Bet type",false,false));
        items.add(new MyBetsModel("TITS vs MNITS",200,"Bet type",false,true));
        items.add(new MyBetsModel("TITS vs MNITS",200,"Bet type",false,false));
        items.add(new MyBetsModel("TITS vs MNITS",200,"Bet type",false,true));
        adapter = new MyBetAdapter(items,getContext());
        betlist.setLayoutManager(new LinearLayoutManager(getContext()));
        betlist.setHasFixedSize(true);
        betlist.setAdapter(adapter);


        return v;
    }

}
