package com.taufik.ministockbit.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.taufik.ministockbit.data.repository.MiniStockbitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MiniStockbitViewModel @Inject constructor(
    repository: MiniStockbitRepository
) : ViewModel() {
    val data = repository.getAllWatchlistResult().cachedIn(viewModelScope)
}