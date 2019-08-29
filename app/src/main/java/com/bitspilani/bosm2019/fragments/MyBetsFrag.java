package com.bitspilani.bosm2019.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.adapters.CustomAdapter;
import com.bitspilani.bosm2019.adapters.MyBetAdapter;
import com.bitspilani.bosm2019.models.Fixture;
import com.bitspilani.bosm2019.models.MyBetsModel;
import com.bitspilani.bosm2019.models.UserBetModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static android.media.CamcorderProfile.get;

public class MyBetsFrag extends Fragment {

    private RecyclerView betlist;
    private MyBetAdapter adapter;
    private ArrayList<UserBetModel> items = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef;
    private SharedPreferences sharedPreferences;

    public MyBetsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_my_bets, container, false);
        betlist = v.findViewById(R.id.myBets_rv);
        sharedPreferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("username","");
        if (userId != null) {
            db.collection("users").document(userId).collection("bets")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {

                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        items.clear();
                            for(QueryDocumentSnapshot doc:queryDocumentSnapshots){
                                UserBetModel ob = new UserBetModel(
                                        doc.getData().get("match_id").toString(),
                                        Double.parseDouble(doc.getData().get("betAmount").toString()),
                                        doc.getData().get("team").toString(),
                                            Integer.parseInt(doc.getData().get("result").toString())
                                );
                                   items.add(ob);
                            }
                            adapter = new MyBetAdapter(items,getContext());
                            betlist.setLayoutManager(new LinearLayoutManager(getContext()));
                            betlist.setHasFixedSize(true);
                            betlist.setAdapter(adapter);
                        }
                    });
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}