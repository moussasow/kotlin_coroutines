package com.mas.kotlincourotines.network

import com.mas.kotlincourotines.data.model.NewsContents
import com.mas.kotlincourotines.data.model.ShopContents
import retrofit2.Response
import retrofit2.http.GET

interface MasApi {

    @GET("/api/v1/news")
    suspend fun fetchData():Response<NewsContents>

    @GET("/api/v1/contents")
    suspend fun fetchShopContents():Response<ShopContents>
}