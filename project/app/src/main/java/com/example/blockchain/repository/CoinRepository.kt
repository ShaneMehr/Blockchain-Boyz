package com.example.blockchain.repository

import com.example.blockchain.network.RetrofitInstance

class CoinRepository {
    // get all coins
    suspend fun getAllCoins(startPaginateAt: Int) =
        RetrofitInstance.api.getAllCoins(startPaginateAt)

}