package com.example.blockchain.viewmodels


import android.animation.TypeEvaluator
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.blockchain.CryptoCurrencyApplication
import com.example.blockchain.models.CoinsResponse
import com.example.blockchain.repository.CoinRepository
import com.example.blockchain.utils.Constants
import com.example.blockchain.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class CryptoListViewModel(
    app : Application,
    val coinRepository: CoinRepository
) : AndroidViewModel(app) {

    val coinsList: MutableLiveData<Resource<CoinsResponse>> = MutableLiveData()
    var startPaginateAt: Int = 0
    var coinListResponse: CoinsResponse? = null

    init{
        getAllCoins()
    }

    // call to get all coins
    fun getAllCoins() = viewModelScope.launch {
        safeGetCoinsCall()
    }

    //handle safe api call on the
    private suspend fun safeGetCoinsCall() {
        coinsList.postValue(Resource.Loading())
        try {
            if(isInternetConnected()){
                val response = coinRepository.getAllCoins(startPaginateAt)
                coinsList.postValue(handleCoinResponse(response))
            }else{
                coinsList.postValue(Resource.Error("No internet Connection"))
            }
        }catch (t: Throwable){
            when(t) {
                is IOException -> coinsList.postValue(Resource.Error("No internet Connection"))
                else -> coinsList.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleCoinResponse(response: Response<CoinsResponse>): Resource<CoinsResponse> {

        if( response.isSuccessful){
            response.body()?.let {coinResponse ->
                // increment paginate start at
                startPaginateAt += Constants.PAGE_LIMIT


                // set inital response
                if( coinListResponse == null )
                    coinListResponse = coinResponse
                else{
                    val newCoins = coinResponse.data
                    val oldCoins = coinListResponse?.data
                    oldCoins?.addAll(newCoins)

                }
                return  Resource.Success( coinListResponse ?: coinResponse)

            }
        }
        return Resource.Error(response.message())
    }

    // handle get all coins response


    private fun isInternetConnected() : Boolean{

        val connectivityManager: ConnectivityManager = getApplication<CryptoCurrencyApplication>()
            .getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

        if( Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> return true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> return true
                else -> return false

            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI ->return true
                    TYPE_MOBILE -> return true
                    TYPE_ETHERNET -> return true
                    else -> return false
                }
            }
            return false
        }
    }

}