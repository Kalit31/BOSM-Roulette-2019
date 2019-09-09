package com.bitspilani.bosm2019.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitspilani.bosm2019.adapters.LeaderBoardAdapter;
import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.models.RankClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoardFrag extends Fragment {

    private RecyclerView leaderlist;
    private LeaderBoardAdapter adapter;
    private TextView yourRank,yourScore,yourName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference leaderRef;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private static ArrayList<RankClass> mArrayList = new ArrayList<>();
    private static ArrayList<RankClass> mArrayListTemp = new ArrayList<>();

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
         yourName = v.findViewById(R.id.your_name);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        String name=user.getDisplayName();
        String userId=user.getUid();

        yourName.setText(name);

        Log.d("name",name);
        if (userId != null) {

            db.collection("users").get().addOnSuccessListener(
                    new OnSuccessListener<QuerySnapshot>() {
                        int i =0;
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                mArrayList.clear();
                                mArrayListTemp.clear();
                            for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                            {
                                RankClass ob = new RankClass(Double.parseDouble(String.valueOf(documentSnapshot.get("wallet"))),
                                        documentSnapshot.get("name").toString(),i+1,documentSnapshot.getId());
                                mArrayList.add(ob);
                                Log.d("mytest",ob.toString());
                                i++;
                            }

                            Comparator<RankClass> compareByWallet = (RankClass o1, RankClass o2) -> (int) (o2.getWallet()- o1.getWallet());
                            Collections.sort(mArrayList, compareByWallet);
                            int j=0;
                            for(RankClass rc: mArrayList){
                                mArrayListTemp.add(new RankClass(rc.getWallet(),rc.getUsername(),
                                        j+1,rc.getId()));
                                j++;
                            }


                            for(RankClass rc:mArrayListTemp){
                                if(rc.getId().equals(userId))
                                {
                                    yourRank.setText(String.valueOf(rc.getRank()));
                                    yourScore.setText(String.valueOf(rc.getWallet()));
                                }
                           }

                            adapter = new LeaderBoardAdapter(mArrayListTemp,getContext());
                            leaderlist.setLayoutManager(new LinearLayoutManager(getContext()));
                            leaderlist.setHasFixedSize(true);
                            leaderlist.setAdapter(adapter);
                        }
                    }
            );

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

/*if(i==0)
                                    mArrayList.add(i,new RankClass(Double.parseDouble(documentSnapshot.get("wallet").toString()),
                                            documentSnapshot.get("name").toString(),i));
                                else{
                                    if(mArrayList.get(i).getWallet() <= Double.parseDouble(documentSnapshot.get("wallet").toString()))
                                        mArrayList.add(i+1,new RankClass(Double.parseDouble(documentSnapshot.get("wallet").toString()),
                                                documentSnapshot.get("name").toString(),i+1));
                                    else {
                                        RankClass ob = new RankClass(mArrayList.get(i).getWallet(),
                                                mArrayList.get(i).getUsername(),i+1);

                                        mArrayList.add(i,new RankClass(Double.parseDouble(documentSnapshot.get("wallet").toString()),
                                                documentSnapshot.get("name").toString(),i));
                                        mArrayList.add(i+1,ob);
                                    }
                                }
                                i++;*/