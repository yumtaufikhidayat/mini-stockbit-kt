package com.taufik.ministockbit.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.taufik.ministockbit.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            // user not logged in, intent to login fragment
//            startActivity(Intent(requireActivity(), AuthActivity::class.java))
//            finish()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}