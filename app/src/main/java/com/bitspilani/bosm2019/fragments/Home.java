package com.bitspilani.bosm2019.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.adapters.CustomAdapter;
import com.bitspilani.bosm2019.models.Fixture;
import com.bitspilani.bosm2019.models.PlaceBetModel;
import com.bitspilani.bosm2019.models.UserBetModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.model.Document;
import com.ramotion.foldingcell.FoldingCell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



public class Home extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference matchRef = db.collection("matches");
    private String TAG = "test1";
    SharedPreferences sharedPreferences;
    private CustomAdapter adapter;
    private String userId;
    private FirebaseAuth mAuth;
    private ArrayList<String> matchesBetId ;
    private  ArrayList<String> matchesId = new ArrayList<>();


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

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        matchesBetId = new ArrayList<>();
        ArrayList<Fixture> fixtures=new ArrayList<>();



        db.collection("users").document(userId).collection("bets")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        matchesBetId.clear();
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                                matchesBetId.add(doc.getId());
                        }
                        db.collection("matches")
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                        fixtures.clear();
                                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                                            Fixture ob = new Fixture(doc.getData().get("college1").toString(),
                                                    doc.getData().get("college2").toString(),
                                                    doc.getData().get("timestamp").toString(),
                                                    doc.getData().get("matchId").toString(),
                                                    doc.getData().get("sports_name").toString());

                                            if(!(matchesBetId.contains(Objects.requireNonNull(doc.getData().get("matchId")).toString())))
                                                //if(matchTime.after(finalCurrentTime))
                                                fixtures.add(ob);
                                        }
                                        recyclerView=view.findViewById(R.id.recycler_view);
                                        recyclerView.setHasFixedSize(false);

                                        // use a linear layout manager
                                        layoutManager = new LinearLayoutManager(getContext());
                                        recyclerView.setLayoutManager(layoutManager);

                                        // specify an adapter (see also next example)
                                        adapter = new CustomAdapter(fixtures, getContext());
                                        recyclerView.setAdapter(adapter);
                                    }
                                });
                    }
                });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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