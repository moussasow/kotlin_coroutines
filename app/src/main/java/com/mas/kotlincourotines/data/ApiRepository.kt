package com.mas.kotlincourotines.data

import com.mas.kotlincourotines.data.result.NewsResult
import com.mas.kotlincourotines.data.result.ShopResult
import com.mas.kotlincourotines.network.MasApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ApiRepository(private val masApi: MasApi) {
    suspend fun fetchData(): NewsResult {
        return fetchData(
            { masApi.fetchData() },
            NewsResult::Success,
            NewsResult::Error
        )
    }

    suspend fun fetchShopContents(): ShopResult {
        return fetchData(
            { masApi.fetchShopContents() },
            ShopResult::Success,
            ShopResult::Error
        )
    }

    private suspend fun <T, R> fetchData(
        fetch: suspend () -> Response<T>,
        success: (T) -> R,
        error: (Exception) -> R
    ): R {
        return withContext(Dispatchers.IO) {
            try {
                val response = fetch()
                if (response.isSuccessful) {
                    success(response.body()!!)
                } else {
                    error(Exception("Network request failed"))
                }
            } catch (e: Exception) {
                error(e)
            }
        }
    }

    /*
    suspend fun fetchData(): NewsResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = masApi.fetchData()
                if (response.isSuccessful) {
                    NewsResult.Success(response.body()!!)
                } else {
                    NewsResult.Error(Exception("Network request failed"))
                }
            } catch (e: Exception) {
                NewsResult.Error(e)
            }
        }
    }

    suspend fun fetchShopContents(): ShopResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = masApi.fetchShopContents()
                if (response.isSuccessful) {
                    ShopResult.Success(response.body()!!)
                } else {
                    ShopResult.Error(Exception("Network request failed"))
                }
            } catch (e: Exception) {
                ShopResult.Error(e)
            }
        }
    } */
}