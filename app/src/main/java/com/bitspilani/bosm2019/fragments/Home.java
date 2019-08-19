package com.bitspilani.bosm2019.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.adapters.CustomAdapter;
import com.bitspilani.bosm2019.models.Fixture;

import java.util.ArrayList;


public class Home extends Fragment{


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    SharedPreferences sharedPreferences;


    public Home() {
        // Required empty public constructor
    }


    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<Fixture> fixtures=new ArrayList();
        fixtures.add(new Fixture("BITS","TITS","SAC","1:30pm"));
        fixtures.add(new Fixture("BITS","DTU","SAC","2:30pm"));
        fixtures.add(new Fixture("MITS","LSR","SAC","3:30pm"));
        fixtures.add(new Fixture("BITS","TITS","SAC","4:30pm"));
        fixtures.add(new Fixture("BITS","TITS","SAC","5:30pm"));
        fixtures.add(new Fixture("BITS","TITS","GymG","6:30pm"));
        fixtures.add(new Fixture("BITS","TITS","SAC","7:30pm"));
        fixtures.add(new Fixture("BITS","TITS","SAC","8:30pm"));
        fixtures.add(new Fixture("BITS","TITS","SAC","9:30pm"));
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CustomAdapter(fixtures,getActivity());
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
