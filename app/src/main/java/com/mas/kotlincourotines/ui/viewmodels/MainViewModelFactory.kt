package com.mas.kotlincourotines.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mas.kotlincourotines.data.ApiRepository

class MainViewModelFactory(private val repository: ApiRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}