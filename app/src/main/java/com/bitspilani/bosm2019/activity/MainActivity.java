package com.bitspilani.bosm2019.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.bitspilani.bosm2019.fragments.LeaderBoardFrag;
import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.fragments.MyBetsFrag;
import com.bitspilani.bosm2019.fragments.RouletteFrag;

public class MainActivity extends AppCompatActivity {

    private Fragment leaderBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leaderBoard = new LeaderBoardFrag();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,leaderBoard).commit();
    }
}