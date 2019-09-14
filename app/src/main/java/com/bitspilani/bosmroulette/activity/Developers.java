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

        list.add(new DevDesc("Ayush Singh","App Developer","Ayush.jpg"));
        list.add(new DevDesc("Gauransh Sawhney","App Developer","Gauransh.jpg"));
        list.add(new DevDesc("Kalit Inani","App Developer","Kalit.jpg"));
        list.add(new DevDesc("Mukund Paliwal","UI/UX Designer","Mukund.png"));
        list.add(new DevDesc("Ayushi Jain","UI/UX Designer","AYUSHI.jpg"));
        list.add(new DevDesc("Sonal Prasad","UI/UX Designer","Sonal.jpg"));
        DevAdapter devAdapter=new DevAdapter(this,list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(devAdapter);




    }
}
