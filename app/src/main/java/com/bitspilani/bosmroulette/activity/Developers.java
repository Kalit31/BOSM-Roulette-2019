package com.bitspilani.bosmroulette.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.adapters.DevAdapter;
import com.bitspilani.bosmroulette.models.DevDesc;

import java.util.ArrayList;

public class Developers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        ArrayList<DevDesc> list=new ArrayList<>();

        list.add(new DevDesc("Ayush Singh","App Developer",R.drawable.ayushnew));
        list.add(new DevDesc("Gauransh Sawhney","App Developer",R.drawable.gausa));
        list.add(new DevDesc("Kalit Inani","App Developer",R.drawable.kalit));
        list.add(new DevDesc("Mukund Paliwal","UI/UX Designer",R.drawable.casinochip));
        list.add(new DevDesc("Ayushi Jain","UI/UX Designer",R.drawable.ayushi));
        list.add(new DevDesc("Sonal Prasad","UI/UX Designer",R.drawable.sopra));
        DevAdapter devAdapter=new DevAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(devAdapter);


    }
}
