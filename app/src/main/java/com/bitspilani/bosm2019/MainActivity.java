package com.bitspilani.bosm2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.SharedPreferences;
import android.net.Uri;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bitspilani.bosm2019.fragments.LeaderBoardFrag;
import com.bitspilani.bosm2019.fragments.MyBetsFrag;
import com.bitspilani.bosm2019.fragments.RouletteFrag;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import re.robz.bottomnavigation.circularcolorreveal.BottomNavigationCircularColorReveal;


public class MainActivity extends AppCompatActivity {
GoogleSignInClient googleSignInClient;


    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment fragment;
    private BubbleNavigationConstraintView buubleNavigation;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buubleNavigation = findViewById(R.id.bottom_nav_bar);
        fragments.add(new BlankFragment());
        fragments.add(new RouletteFrag());
        fragments.add(new MyBetsFrag());
        fragments.add(new LeaderBoardFrag());
        fragments.add(new Home());
        fragment = fragments.get(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        buubleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                fragment = fragments.get(position);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
      //  Button button=findViewById(R.id.button);
     //   TextView textView=findViewById(R.id.textView4);
      //  textView.setText(mAuth.getCurrentUser().getEmail());
/*        button.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });*/



    }


}
