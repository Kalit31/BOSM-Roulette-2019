package com.bitspilani.bosmroulette.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitspilani.bosmroulette.LinePageIndicatorDecoration;
import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.activity.LoginActivity;
import com.bitspilani.bosmroulette.adapters.TrendingAdapter;
import com.bitspilani.bosmroulette.models.Fixture;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


public class BlankFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String mParam1;
    private String mParam2;
    private Button signOut;
    private GoogleSignInClient mGoogleSignInClient;
    TextView balance, name;
    private FirebaseAuth mAuth;
    private RecyclerView rv;
    private TrendingAdapter adapter;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        rv = v.findViewById(R.id.trending_rv);
        signOut = v.findViewById(R.id.signOut);
        balance = v.findViewById(R.id.balance);
        name = v.findViewById(R.id.username);

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).addSnapshotListener(
                new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        double wallet = Double.parseDouble(documentSnapshot.get("wallet").toString());
                        balance.setText(String.valueOf(wallet));
                        name.setText(documentSnapshot.get("name").toString());
                    }
                }
        );
        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                balance.setText(documentSnapshot.get("wallet").toString());
            }
        });

        setUpRecyclerView();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return v;

    }


    private void setUpRecyclerView() {
        Query query = db.collection("matches").orderBy("total", Query.Direction.DESCENDING).limit(5);
        FirestoreRecyclerOptions<Fixture> options = new FirestoreRecyclerOptions.Builder<Fixture>()
                .setQuery(query,Fixture.class)
                .build();
        adapter = new TrendingAdapter(options,getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL,false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        rv.addItemDecoration(new LinePageIndicatorDecoration());
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
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