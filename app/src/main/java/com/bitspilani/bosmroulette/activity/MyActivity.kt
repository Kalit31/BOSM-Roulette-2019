package com.bitspilani.bosmroulette.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.bitspilani.bosmroulette.R
import com.bitspilani.bosmroulette.models.UserBetModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.SetOptions
import java.util.*

class MyActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val mStatusTextView: TextView? = null
    private val mProgressDialog: ProgressDialog? = null
    internal var gmail: TextView? = null
    //   internal var button: SignInButton
  //  lateinit var sharedPref: SharedPreferences
    lateinit var button: SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //     userBetsList = ArrayList()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        button = findViewById(R.id.googlelogin)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth!!.currentUser



        if (user != null) {
//            val editor = sharedPref.edit()
//            editor.putString("username", user.uid)
//            editor.putString("name", user.displayName)
//            editor.apply()
            val intent=Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        button.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = mAuth!!.currentUser
                        updateUI(user)
                        //startActivity(Intent(this, HomeActivity::class.java))
                        //finish()

                    } else {

                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)

                        Toast.makeText(applicationContext, R.string.app_name, Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            val settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()
            db.firestoreSettings = settings


            db.collection("users").whereEqualTo("email", user.email)
                    .addSnapshotListener { queryDocumentSnapshots, e ->
                        if (queryDocumentSnapshots!!.documents == null) {
                          //  val editor = sharedPref.edit()
//                            editor.putString("username", user.uid)
//                            editor.apply()
                            val data = HashMap<String, Any>()
                            data["email"] = user.email!!
                            data["name"] = user.displayName!!
                            data["username"] = user.uid
                            data["wallet"] = 1000.0
                            data["score"] = 0.0
                            data["slot_time"] = FieldValue.serverTimestamp()
                            val stringTokenizer = StringTokenizer(user.email, "@")
                            val qrcode = stringTokenizer.nextToken()
                            data["qr_code"] = qrcode

                            Log.d("test2", user.uid)



                            db.collection("users").document(user.uid).set(data, SetOptions.merge()).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent=Intent(this, HomeActivity::class.java)
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(intent)
                                    finish()
                                } else {

                                    Toast.makeText(this, "Connection error!", Toast.LENGTH_SHORT).show()

                                }
                            }
                        } else {
                            val intent=Intent(applicationContext, HomeActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                        }
                    }

        }

    }

    companion object {
        private val TAG = "SignInActivity"
        private val RC_SIGN_IN = 9001
        lateinit var userBetsList: ArrayList<UserBetModel>
    }
}
