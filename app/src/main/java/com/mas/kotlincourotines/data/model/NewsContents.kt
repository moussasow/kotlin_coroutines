package com.mas.kotlincourotines.data.model

data class NewsContents(
    val contents: List<Content>,
    val limit: Int,
    val offset: Int,
    val totalCount: Int
)