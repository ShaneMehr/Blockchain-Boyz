package com.example.blockchain.models

import com.google.gson.annotations.SerializedName

data class Coins(
    @SerializedName("name") val name: String? ,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("price_usd") val price: String?,
    @SerializedName("rank") val rank: String?,
    @SerializedName("percent_change_7d") val percentage: String?
)