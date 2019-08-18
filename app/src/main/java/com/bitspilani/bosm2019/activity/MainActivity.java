package com.bitspilani.bosm2019.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.bitspilani.bosm2019.Home;
import com.bitspilani.bosm2019.fragments.LeaderBoardFrag;
import com.bitspilani.bosm2019.R;
import com.bitspilani.bosm2019.fragments.MyBetsFrag;
import com.bitspilani.bosm2019.fragments.RouletteFrag;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment fragment;
    private BubbleNavigationConstraintView buubleNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buubleNavigation = findViewById(R.id.bottom_nav_bar);
        fragments.add(new LeaderBoardFrag());
        fragments.add(new RouletteFrag());
        fragments.add(new MyBetsFrag());
        fragments.add(new LeaderBoardFrag());
        fragments.add(new RouletteFrag());
        fragment = fragments.get(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        buubleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                fragment = fragments.get(position);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });


    }
}