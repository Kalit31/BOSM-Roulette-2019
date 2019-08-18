package com.bitspilani.bosm2019;

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

import java.util.ArrayList;


public class Home extends Fragment{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String mParam1;
    private String mParam2;
    SharedPreferences sharedPreferences;


    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
