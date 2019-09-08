package com.bitspilani.bosm2019.fragments


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.bitspilani.bosm2019.R
import com.bitspilani.bosm2019.activity.MyActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WalletFrag : Fragment() {
    internal var sharedPreferences: SharedPreferences? = null
    private var mAuth: FirebaseAuth? = null
    private val db = FirebaseFirestore.getInstance()
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var signOut: Button? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    lateinit var balance: TextView
    lateinit var name: TextView
    lateinit var betplaced: TextView
    lateinit var betwon: TextView
    internal var walletbalance: Int = 0
    internal var betAmount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_blank, container, false)
        mAuth = FirebaseAuth.getInstance()
        signOut = v.findViewById(R.id.signOut)
        balance = v.findViewById(R.id.balance)

        name = v.findViewById(R.id.name)
        betplaced = v.findViewById(R.id.bet_placed)
        betwon = v.findViewById(R.id.betwon)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        name.text = mAuth!!.currentUser!!.displayName
        val userId = mAuth!!.currentUser!!.uid
        db.collection("users").document(userId).addSnapshotListener { documentSnapshot, e ->
            val wallet = java.lang.Double.parseDouble(documentSnapshot!!.get("wallet")!!.toString())
            balance.text = wallet.toString()
        }
        db.collection("users").document(userId).collection("bets").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var count = 0
                var count1 = 0
                for (document in task.result!!) {
                    count++
                    if (Integer.parseInt(document.get("result")!!.toString()) == 1) {
                        count1++
                    }
                }
                betplaced.text = count.toString()
                betwon.text = count1.toString()

            }
        }

        signOut!!.setOnClickListener {
            mGoogleSignInClient!!.signOut()
            mAuth!!.signOut()
            startActivity(Intent(activity, MyActivity::class.java))
        }

        return v
    }

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): WalletFrag {
            val fragment = WalletFrag()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    /*                      double wallet = Double.parseDouble(documentSnapshot.get("wallet").toString());
                        balance.setText(String.valueOf(wallet));*/
}// Required empty public constructor
