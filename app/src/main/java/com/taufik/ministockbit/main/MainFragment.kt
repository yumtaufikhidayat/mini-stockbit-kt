package com.taufik.ministockbit.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.taufik.ministockbit.R
import com.taufik.ministockbit.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        const val TAG = "MAIN_FRAGMENT"
    }

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

        setHasOptionsMenu(true)

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
            Log.d(TAG, "checkUser: ")
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val item = menu.findItem(R.id.nav_contact_us)
        item.isVisible = false

        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}