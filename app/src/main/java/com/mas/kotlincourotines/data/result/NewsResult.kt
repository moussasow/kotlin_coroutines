package com.mas.kotlincourotines.data.result

import com.mas.kotlincourotines.data.model.NewsContents

sealed class NewsResult {
    data class Success(val data: NewsContents) : NewsResult()
    data class Error(val exception: Exception) : NewsResult()
}
