package com.bitspilani.bosm2019.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.bitspilani.bosm2019.R
import com.bitspilani.bosm2019.fragments.*
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class HomeActivity : AppCompatActivity() {

    private val fragments = ArrayList<Fragment>()
    private var fragment: Fragment? = null
    private  val ID_WALLET = 1
    private  val ROULETTE = 2
    private  val HOME = 3
    private  val MY_BETS = 4
    private  val LEADERBOARD = 5
    private  val MORE = 6
    private var selectedFrag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main :RelativeLayout?=findViewById(R.id.Home);

        val bottomNavigation:MeowBottomNavigation = findViewById(R.id.bottom_nav)
        bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_home))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_account_balance_wallet_black_24dp))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_coins))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_roulette))
        bottomNavigation.add(MeowBottomNavigation.Model(5, R.drawable.ic_leaderboard))
        bottomNavigation.add(MeowBottomNavigation.Model(6, R.drawable.ic_more))

        fragments.add(Home())
        fragments.add(BlankFragment())
        fragments.add(MyBetsFrag())
        fragments.add(RouletteFrag())
        fragments.add(LeaderBoardFrag())
        fragments.add(RouletteFrag())
        fragment = fragments[0]
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment!!).commit()


        bottomNavigation.setOnShowListener {
            selectedFrag = when (it.id){
                ID_WALLET -> 0
                ROULETTE -> 1
                HOME -> 2
                MY_BETS -> 3
                LEADERBOARD -> 4
                MORE -> 5
                else -> 0
            }
            fragment = fragments[selectedFrag]
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment!!).commit()
            if(selectedFrag==3)
                main!!.setBackgroundResource(R.drawable.roulette_bg);
            else
                main!!.setBackgroundResource(R.drawable.bg);
        }
        if(selectedFrag==1)
            main!!.setBackgroundResource(R.drawable.roulette_bg);

        bottomNavigation.setOnClickMenuListener {        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()
        //  googleSignInClient = GoogleSignIn.getClient(this, gso)
        val mAuth = FirebaseAuth.getInstance()
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
