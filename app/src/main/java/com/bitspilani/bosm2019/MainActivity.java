package com.bitspilani.bosm2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Home.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Home home=new Home();
        fragmentTransaction.add(R.id.Home,home);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }
}
