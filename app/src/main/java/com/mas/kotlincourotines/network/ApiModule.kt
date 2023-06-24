package com.mas.kotlincourotines.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiModule {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://diga.pythonanywhere.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val masApi: MasApi = retrofit.create(MasApi::class.java)
}