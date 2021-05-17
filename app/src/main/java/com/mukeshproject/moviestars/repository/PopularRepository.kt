package com.mukeshproject.moviestars.repository

import com.mukeshproject.moviestars.network.ApiClient
import com.mukeshproject.moviestars.network.Network
import com.mukeshproject.moviestars.network.popular.ResponseTrending
import com.mukeshproject.moviestars.viewmodel.PopularViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopularRepository(private val callback: PopularViewModel) {

    val scope = CoroutineScope(Dispatchers.IO)

    fun getListofPopular(page: Int) {

        scope.launch  {

            val apiClient = Network.getRetrofitInstance().create(ApiClient::class.java)
            val call = apiClient.getPopular(api_key, page)

            call.enqueue(callback)

        }

    }

    companion object {
        val api_key = "f93f2306dd57a8d5c1932faa0774cd16"
    }
}


