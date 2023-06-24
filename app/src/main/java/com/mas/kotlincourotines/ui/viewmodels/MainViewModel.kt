package com.mas.kotlincourotines.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mas.kotlincourotines.data.ApiRepository
import com.mas.kotlincourotines.data.result.NewsResult
import com.mas.kotlincourotines.data.result.ShopResult
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ApiRepository) : ViewModel() {
    private val _data = MutableLiveData<NewsResult>()
    val data: LiveData<NewsResult> = _data
    private val _shopContents = MutableLiveData<ShopResult>()
    val shopContents: LiveData<ShopResult> = _shopContents
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadData() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.fetchData()
            _data.value = result
            _loading.value = false
        }
    }

    fun fetchShopContents() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.fetchShopContents()
            _shopContents.value = result
            _loading.value = false
        }
    }
}