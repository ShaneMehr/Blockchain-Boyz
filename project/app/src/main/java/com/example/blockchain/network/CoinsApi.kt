package com.example.blockchain.network

import com.example.blockchain.models.CoinsResponse
import com.example.blockchain.utils.Constants.Companion.PAGE_LIMIT
import kotlinx.coroutines.Deferred
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsApi {


    // get information on all the coins
    @GET("tickers/")
    suspend fun getAllCoins(
        @Query("start") startPaginateAt: Int = 0,
        @Query("limit") paginateLimit: Int = PAGE_LIMIT
    ) : Response<CoinsResponse>

}