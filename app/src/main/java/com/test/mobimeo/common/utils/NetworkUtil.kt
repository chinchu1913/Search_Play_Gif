package com.test.mobimeo.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface NetworkUtil {
    fun getNetworkLiveData(): LiveData<Boolean>
}

class NetworkUtilsImpl(private val context: Context) : ConnectivityManager.NetworkCallback(),
    NetworkUtil {

    private val networkLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun getNetworkLiveData(): LiveData<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkLiveData = MutableLiveData<Boolean>()

        val networkCallbacks = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val networkCapability = connectivityManager.getNetworkCapabilities(network)
                val isConnected =
                    networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
                networkLiveData.postValue(isConnected)
            }

            override fun onLost(network: Network) {
                networkLiveData.postValue(false)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallbacks)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallbacks)
        }

        val isConnected = connectivityManager.activeNetwork != null
        networkLiveData.postValue(isConnected)

        return networkLiveData
    }


    override fun onAvailable(network: Network) {
        networkLiveData.postValue(true)
    }

    override fun onLost(network: Network) {
        networkLiveData.postValue(false)
    }
}