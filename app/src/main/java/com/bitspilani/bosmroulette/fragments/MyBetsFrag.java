package com.bitspilani.bosmroulette.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.adapters.MyBetAdapter;
import com.bitspilani.bosmroulette.models.UserBetModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyBetsFrag extends Fragment {

    private RecyclerView betlist;
    private MyBetAdapter adapter;
    private ArrayList<UserBetModel> items = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef;
    private SharedPreferences sharedPreferences;
    private ArrayList<UserBetModel> it = new ArrayList<>();
    private double wallet;
    private FirebaseAuth mAuth;
    private double betAmount;
    private ProgressBar progressBar;

    public MyBetsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_bets, container, false);
        betlist = v.findViewById(R.id.myBets_rv);
        progressBar = v.findViewById(R.id.progressbar);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        if (userId != null) {
            db.collection("users").document(userId).collection("bets")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            it.clear();
                            items.clear();
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                UserBetModel ob = new UserBetModel(
                                        doc.getData().get("match_id").toString(),
                                        Double.parseDouble(doc.getData().get("betAmount").toString()),
                                        doc.getData().get("team1").toString(),
                                        doc.getData().get("team2").toString(),
                                        Integer.parseInt(doc.getData().get("bettedOn").toString()),
                                        doc.getData().get("game").toString(),
                                        Boolean.parseBoolean(doc.get("update").toString()),
                                        Integer.parseInt(doc.getData().get("score1").toString()),
                                        Integer.parseInt(doc.getData().get("score2").toString()),
                                        Integer.parseInt(doc.getData().get("result").toString())
                                );
                                if (!(Boolean.parseBoolean(doc.get("update").toString()))) {
                                    it.add(ob);
                                }
                                items.add(ob);

                            }
                            adapter = new MyBetAdapter(items, getContext());
                            betlist.setLayoutManager(new LinearLayoutManager(getContext()));
                            betlist.setHasFixedSize(true);
                            betlist.setAdapter(adapter);

                            progressBar.setVisibility(View.INVISIBLE);

                            for (UserBetModel item : it) {

                                Log.d("itemid", item.getMatch_id());
                                db.collection("matches").document(item.getMatch_id())
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {


                                            DocumentSnapshot document = task.getResult();
                                            Log.d("myPrint", "printing here");
                                            boolean is_result = Boolean.parseBoolean(document.get("is_result").toString());
                                            int score1 = Integer.parseInt(document.get("score1").toString());
                                            int score2 = Integer.parseInt(document.get("score2").toString());
                                            int team1 = Integer.parseInt(document.get("team1").toString());
                                            int team2 = Integer.parseInt(document.get("team2").toString());
                                            int win_team, lose_team;

                                            Map<String, Object> scoreMap = new HashMap<>();
                                            scoreMap.put("score1", score1);
                                            scoreMap.put("score2", score2);
                                            db.collection("users").document(userId).collection("bets")
                                                    .document(item.getMatch_id()).set(scoreMap, SetOptions.merge());

                                            if (Integer.parseInt(document.getData().get("winner").toString()) == 0) {
                                                win_team = team1;
                                                lose_team = team1;
                                            } else {
                                                win_team = team2;
                                                lose_team = team1;
                                            }

                                            if (is_result) {
                                                if (item.getBettedOn() == Integer.parseInt(document.getData().get("winner").toString())) {
                                                    betAmount = item.getBetAmount();

                                                    db.collection("users").document(userId)
                                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot doc = task.getResult();
                                                                wallet = Double.parseDouble(doc.get("wallet").toString());
                                                                if (Boolean.parseBoolean(doc.get("bonus").toString()))
                                                                    wallet = wallet + (betAmount * 1.25 + betAmount * (1 - (double) win_team / (lose_team * 100))) * 1.1;
                                                                else
                                                                    wallet = wallet + betAmount * 1.25 + betAmount * (1 - (double) win_team / (lose_team * 100));
                                                                Map<String, Double> newWallet = new HashMap<>();
                                                                newWallet.put("wallet", wallet);

                                                                db.collection("users").document(userId).set(newWallet, SetOptions.merge());
                                                                Map<String, Object> map = new HashMap<>();
                                                                map.put("update", true);
                                                                map.put("result", item.getBettedOn());

                                                                db.collection("users").document(userId).collection("bets")
                                                                        .document(item.getMatch_id()).set(map, SetOptions.merge());
                                                            }
                                                        }
                                                    });
                                                    db.collection("users").document(userId).collection("bets").document(item.getMatch_id()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            DocumentSnapshot snapshot = task.getResult();
                                                            Double bet = Double.parseDouble(snapshot.get("betAmount").toString());

                                                            bet = betAmount * .25 + betAmount * (1 - (double) win_team / (lose_team * 100));
                                                            HashMap<String, Object> hashMap = new HashMap<>();
                                                            hashMap.put("betAmount", bet);
                                                            db.collection("users").document(userId).collection("bets").document(item.getMatch_id()).set(hashMap, SetOptions.merge());


                                                        }
                                                    });
                                                } else if (Integer.parseInt(document.getData().get("winner").toString()) == 2) {
                                                    db.collection("users").document(userId)
                                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot doc = task.getResult();
                                                                wallet = Double.parseDouble(doc.get("wallet").toString());
                                                                if (Boolean.parseBoolean(doc.get("bonus").toString()))
                                                                    wallet = wallet + betAmount;
                                                                else
                                                                    wallet = wallet + betAmount;
                                                                Map<String, Double> newWallet = new HashMap<>();
                                                                newWallet.put("wallet", wallet);

                                                                db.collection("users").document(userId).set(newWallet, SetOptions.merge());
                                                                Map<String, Object> map = new HashMap<>();
                                                                map.put("update", true);
                                                                map.put("result", item.getBettedOn());

                                                                db.collection("users").document(userId).collection("bets")
                                                                        .document(item.getMatch_id()).set(map, SetOptions.merge());
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    db.collection("users").document(userId)
                                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot doc = task.getResult();

                                                                wallet = Double.parseDouble(doc.get("wallet").toString());
                                                                if (Boolean.parseBoolean(doc.get("loss").toString()))
                                                                    wallet = wallet + betAmount;
                                                                else
                                                                    wallet = wallet + betAmount * 0.25;

                                                                Map<String, Double> newWallet = new HashMap<>();
                                                                newWallet.put("wallet", wallet);

                                                                db.collection("users").document(userId).set(newWallet, SetOptions.merge());

                                                                Map<String, Object> map = new HashMap<>();
                                                                map.put("update", true);
                                                                map.put("result", Integer.parseInt(document.getData().get("winner").toString()));

                                                                db.collection("users").document(userId).collection("bets")
                                                                        .document(item.getMatch_id()).set(map, SetOptions.merge());
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                });
                            }
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