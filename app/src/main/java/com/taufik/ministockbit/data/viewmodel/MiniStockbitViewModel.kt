package com.taufik.ministockbit.data.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.taufik.ministockbit.data.repository.MiniStockbitRepository

class MiniStockbitViewModel @ViewModelInject constructor(
    private val repository: MiniStockbitRepository
) : ViewModel() {

}