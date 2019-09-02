package com.bitspilani.bosm2019.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bitspilani.bosm2019.adapters.MyBetAdapter;
import com.bitspilani.bosm2019.models.UserBetModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import io.grpc.Server;

public class WinningService extends Service {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context context= this;
  // SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
   // SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    private ArrayList<UserBetModel> items = new ArrayList<>();
    private double wallet;
    private double betAmount;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        String userId = "Y7734dE6qXeWk2j9F1ZiQoJxbW53";
        db.collection("users").document(userId).collection("bets")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        items.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots)
                        {
                            if(!(Boolean.parseBoolean(doc.get("update").toString()))){
                                UserBetModel ob = new UserBetModel(
                                        doc.get("match_id").toString(),
                                        Double.parseDouble(doc.get("betAmount").toString()),
                                        doc.get("team").toString(),
                                        Integer.parseInt(doc.get("result").toString()),
                                        Boolean.parseBoolean(doc.get("update").toString())
                                );
                                items.add(ob);
                            }
                        }
                        for(UserBetModel item:items){
                            Log.d("itemid",item.getMatch_id());
                            db.collection("matches").document(item.getMatch_id())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        DocumentSnapshot document = task.getResult();
//                                        if(document.exists())
//                                            Log.d("mybets",document.getData().toString());
//                                        else
//                                            Log.d("mybets","No Document");
                                        Log.d("myPrint","printing here");

                                        boolean is_result = Boolean.parseBoolean(document.get("is_result").toString());
                                        if(is_result){
                                            if (item.getResult() == Integer.parseInt(document.getData().get("winner").toString())){
                                                betAmount = item.getBetAmount();
                                                db.collection("users").document(userId)
                                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            DocumentSnapshot doc = task.getResult();
                                                            wallet = Double.parseDouble(doc.get("wallet").toString());
                                                            wallet = wallet + betAmount*1.5;

                                                            Map<String,Double> newWallet =new HashMap<>();
                                                            newWallet.put("wallet",wallet);
                                                            Log.d("myPrint","printing here");
                                                            db.collection("users").document(userId).set(newWallet, SetOptions.merge());
                                                            Map<String,Boolean> map = new HashMap<>();
                                                            map.put("update",true);

                                                            db.collection("users").document(userId).collection("bets")
                                                                    .document(item.getMatch_id()).set(map,SetOptions.merge());
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

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }

}



/*db.collection("users").document(userId).collection("bets")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        items.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots)
                        {
                            if(!(Boolean.parseBoolean(doc.get("update").toString()))){
                                UserBetModel ob = new UserBetModel(
                                        doc.get("match_id").toString(),
                                        Double.parseDouble(doc.get("betAmount").toString()),
                                        doc.get("team").toString(),
                                        Integer.parseInt(doc.get("result").toString()),
                                        Boolean.parseBoolean(doc.get("update").toString())
                                );
                                items.add(ob);
                            }
                        }
                        for (UserBetModel item : items) {
                            db.collection("matches").document(item.getMatch_id()).addSnapshotListener(
                                    new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                            boolean is_result = Boolean.parseBoolean(documentSnapshot.getData().get("is_result").toString());
                                            Log.d("update3",String.valueOf(is_result));
                                            if (is_result) {
                                                if (item.getResult() == Integer.parseInt(documentSnapshot.getData().get("winner").toString()))
                                                   betAmount = item.getBetAmount();
                                                   db.collection("users").document(userId).addSnapshotListener(
                                                           new EventListener<DocumentSnapshot>() {
                                                               @Override
                                                               public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                                  wallet = Double.parseDouble(documentSnapshot.getData().get("wallet").toString());
                                                                  wallet = wallet + betAmount*1.5;

                                                                  Map<String,Double> newWallet =new HashMap<>();
                                                                  newWallet.put("wallet",wallet);

                                                                  db.collection("users").document(userId).set(newWallet, SetOptions.merge());
                                                                    Map<String,Boolean> map = new HashMap<>();
                                                                    map.put("update",true);

                                                                   db.collection("users").document(userId).collection("bets")
                                                                   .document(item.getMatch_id()).set(map,SetOptions.merge());

                                                               }
                                                           }
                                                   );
                                            }
                                        }
                                    }
                            );

                        }

                    }
                });*/