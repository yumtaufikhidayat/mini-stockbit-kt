package com.taufik.ministockbit.auth.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.taufik.ministockbit.R
import com.taufik.ministockbit.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavHostFragment()

        initActionBar()
    }

    /*
    * Set action bar using navigation host fragment
    */
    private fun setNavHostFragment() {
        binding.apply {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
            navController = navHostFragment.findNavController()
            setSupportActionBar(toolbarAuth)
            setupActionBarWithNavController(navController)
        }
    }

    /*
    * Set action bar shadow and text color of title
    */
    private fun initActionBar() {
        binding.apply {
            supportActionBar?.elevation = 0F
            toolbarAuth.setTitleTextColor(ContextCompat.getColor(this@AuthActivity, R.color.white))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}