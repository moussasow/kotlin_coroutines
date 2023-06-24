package com.mas.kotlincourotines.data.result

import com.mas.kotlincourotines.data.model.ShopContents

sealed class ShopResult {
    data class Success(val data: ShopContents) : ShopResult()
    data class Error(val exception: Exception) : ShopResult()
}
