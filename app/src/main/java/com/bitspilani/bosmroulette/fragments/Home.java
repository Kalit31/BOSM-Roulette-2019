package com.bitspilani.bosmroulette.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosmroulette.BetDialog;
import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.adapters.CustomAdapter;
import com.bitspilani.bosmroulette.models.FixtureModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class Home extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference matchRef = db.collection("matches");
    private String TAG = "test1";
    SharedPreferences sharedPreferences;
    private Date d1, d2;
    private CustomAdapter adapter;
    ImageView baxter;
    TextView textView;
    private String userId;
    private FirebaseAuth mAuth;
    private ArrayList<String> matchesBetId;
    private ArrayList<String> matchesId = new ArrayList<>();


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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progressbarmatches);
        Sprite viewloader = new Wave();
        textView = view.findViewById(R.id.textView3);
        baxter = view.findViewById(R.id.imageView);
        textView.setVisibility(View.INVISIBLE);
        baxter.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminateDrawable(viewloader);
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        matchesBetId = new ArrayList<>();
        ArrayList<FixtureModel> fixtures = new ArrayList<>();
        //   DateFormat dateFormat=new DateFormat("MMM dd HH:mm:ss",Locale) ;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        Log.d("mytime", currentTime.toString());
        try {
            d1 = sdf.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        db.collection("users").document(userId).collection("bets")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    int i = 0;

                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        matchesBetId.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            matchesBetId.add(doc.getId());
                        }
                        db.collection("matches")
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                        fixtures.clear();
                                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                            FixtureModel ob = new FixtureModel(doc.getData().get("college1").toString(),
                                                    doc.getData().get("college2").toString(),
                                                    doc.getData().get("timestamp").toString(),
                                                    doc.getData().get("matchId").toString(),
                                                    doc.getData().get("sports_name").toString(),
                                                    i);
                                            if (!(matchesBetId.contains(Objects.requireNonNull(doc.getData().get("matchId")).toString()))) {
                                                try {
                                                    d2 = sdf.parse(ob.getTimestamp());
                                                } catch (ParseException e1) {
                                                    e1.printStackTrace();
                                                }
                                                if (d2.getTime() >= d1.getTime())
                                                    fixtures.add(ob);
                                                i++;
                                            }


                                        }

                                        Comparator<FixtureModel> compareByWallet = (FixtureModel o1, FixtureModel o2) -> {
                                            try {
                                                int i = sdf.parse(o1.getTimestamp()).compareTo(sdf.parse(o2.getTimestamp()));
                                                return i;
                                            } catch (ParseException ex) {
                                                ex.printStackTrace();
                                            }
                                            return 0;
                                        };

                                        Collections.sort(fixtures, compareByWallet);

                                        recyclerView = view.findViewById(R.id.recycler_view);
                                        recyclerView.setHasFixedSize(false);

                                        // use a linear layout manager
                                        layoutManager = new LinearLayoutManager(getContext());
                                        recyclerView.setLayoutManager(layoutManager);

                                        // specify an adapter (see also next example)
                                        adapter = new CustomAdapter(fixtures, getContext());
                                        recyclerView.setAdapter(adapter);

                                        adapter.setOnItemClickListener(new CustomAdapter.ClickListener() {
                                            @Override
                                            public void onItemClicked(int position, View v) {
                                                Log.d(TAG, "onItemClick position: " + position);
                                                db.collection("users").document(userId).get().addOnSuccessListener(
                                                        new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                double wallet = Double.parseDouble(documentSnapshot.get("wallet").toString());
                                                                BetDialog betDialog = new BetDialog(getContext(), fixtures.get(position), wallet);
                                                                betDialog.show();
                                                                Window window = betDialog.getWindow();
                                                                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 720);
                                                                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                                            }
                                                        }
                                                );
                                            }
                                        });

                                        progressBar.setVisibility(View.INVISIBLE);
                                        if (adapter.getItemCount() == 0) {
                                            textView.setVisibility(View.VISIBLE);
                                            baxter.setVisibility(View.VISIBLE);
                                        } else {
                                            textView.setVisibility(View.INVISIBLE);
                                            baxter.setVisibility(View.INVISIBLE);
                                        }
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