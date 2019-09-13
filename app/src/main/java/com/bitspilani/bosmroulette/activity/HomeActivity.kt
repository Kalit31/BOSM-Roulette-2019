package com.bitspilani.bosmroulette.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.bitspilani.bosmroulette.R
import com.bitspilani.bosmroulette.fragments.*
import com.bitspilani.bosmroulette.models.ZoomOutSlideTransformer
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.fragment_home.*
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
        bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_account_balance_wallet_black_24dp))
        bottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.ic_my_bets_1_svg))
        bottomNavigation.add(MeowBottomNavigation.Model(3,R.drawable.ic_home ))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_roulette))
        bottomNavigation.add(MeowBottomNavigation.Model(5, R.drawable.ic_leaderboard))
        bottomNavigation.add(MeowBottomNavigation.Model(6, R.drawable.ic_more))

        fragments.add(BlankFragment())
        fragments.add(MyBetsFrag())
        fragments.add(Home())
        fragments.add(RouletteFrag())
        fragments.add(LeaderBoardFrag())
        fragments.add(More())
        fragment = fragments[0]
        bottomNavigation.show(ID_WALLET)
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
                main!!.setBackgroundResource(R.drawable.roulette_bg)
            else
                main!!.setBackgroundResource(R.drawable.bg)
        }
        bottomNavigation.setOnClickMenuListener {        }
    }

    override fun onBackPressed() {
        if(RouletteFrag.notallowback==false)
        super.onBackPressed()
    }
}
