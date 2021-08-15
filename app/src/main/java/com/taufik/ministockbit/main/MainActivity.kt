package com.taufik.ministockbit.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.taufik.ministockbit.auth.activity.AuthActivity
import com.taufik.ministockbit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()

        checkUser()

        setLogout()
    }

    /*
    * Initialize firebase variables
    */
    private fun initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    /*
    * Check user logged in
    */
    private fun checkUser() {
        // get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        } else {
            // user logged in and get user info
            binding.apply {
                val email = firebaseUser.email
                tvLoggedIn.text = String.format("Kamu telah terdaftar sebagai \n %s", email)
            }
        }
    }

    /*
    * Logout from account
    */
    private fun setLogout() {
        binding.apply {
            btnLogout.setOnClickListener {
                firebaseAuth.signOut()
                checkUser()
            }
        }
    }
}