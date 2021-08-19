package com.taufik.ministockbit.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.taufik.ministockbit.R
import com.taufik.ministockbit.data.viewmodel.MiniStockbitViewModel
import com.taufik.ministockbit.databinding.FragmentMainBinding
import com.taufik.ministockbit.ui.main.adapter.MainAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adapterMain: MainAdapter
    private val viewModel by viewModels<MiniStockbitViewModel>()

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

        setWatchlistData()
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
            Log.d(TAG, "checkUser: $firebaseUser")
        } else {
            Log.d(TAG, "checkUser: $firebaseUser")
        }
    }

    /*
    * Set all watchlist data
    */
    private fun setWatchlistData() {

        adapterMain = MainAdapter()

        binding.apply {
            rvMain.layoutManager = LinearLayoutManager(requireActivity())
            rvMain.setHasFixedSize(true)
            rvMain.adapter = adapterMain
        }

        viewModel.data.observe(viewLifecycleOwner) {
            if (it != null) {
                adapterMain.submitData(viewLifecycleOwner.lifecycle, it)
            } else {
                Log.d(TAG, "setWatchlistData: $it")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val item = menu.findItem(R.id.nav_contact_us)
        item.isVisible = false

        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            // Logout from account
            R.id.nav_exit -> firebaseAuth.signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}