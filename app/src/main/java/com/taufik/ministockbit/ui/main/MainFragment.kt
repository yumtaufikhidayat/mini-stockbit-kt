package com.taufik.ministockbit.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.taufik.ministockbit.R
import com.taufik.ministockbit.data.viewmodel.MiniStockbitViewModel
import com.taufik.ministockbit.databinding.FragmentMainBinding
import com.taufik.ministockbit.ui.main.adapter.MainAdapter
import com.taufik.ministockbit.ui.main.adapter.MainLoadStateAdapter
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

        swipeRefreshLayoutOnRefreshListener()

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

    private fun swipeRefreshLayoutOnRefreshListener(){
        binding.apply {
            swipeRefreshMain.setOnRefreshListener { setWatchlistData() }
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
            rvMain.itemAnimator = null
            rvMain.adapter = adapterMain.withLoadStateHeaderAndFooter(
                header = MainLoadStateAdapter { adapterMain.retry() },
                footer = MainLoadStateAdapter { adapterMain.retry() },
            )
            btnRetry.setOnClickListener {
                adapterMain.retry()
            }
        }

        viewModel.data.observe(viewLifecycleOwner) {
            if (it != null) {
                adapterMain.submitData(viewLifecycleOwner.lifecycle, it)
            } else {
                Log.d(TAG, "setWatchlistData: $it")
            }
        }

        adapterMain.addLoadStateListener { loadSate ->
            binding.apply {
                swipeRefreshMain.isRefreshing = loadSate.source.refresh is LoadState.Loading
                progressBarMain.isVisible = loadSate.source.refresh is LoadState.Loading
                rvMain.isVisible = loadSate.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadSate.source.refresh is LoadState.Error

                // for empty view
                if (loadSate.source.refresh is LoadState.NotLoading
                    && loadSate.append.endOfPaginationReached
                    && adapterMain.itemCount < 1
                ) {
                    rvMain.isVisible = false
                    tvNoResultError.isVisible = true
                } else {
                    tvNoResultError.isVisible = false
                }
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