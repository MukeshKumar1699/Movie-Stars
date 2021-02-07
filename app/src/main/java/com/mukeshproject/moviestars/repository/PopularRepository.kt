package com.mukeshproject.moviestars.repository

import com.mukeshproject.moviestars.network.ApiClient
import com.mukeshproject.moviestars.network.Network
import com.mukeshproject.moviestars.viewmodel.PopularViewModel

class PopularRepository(private val callback: PopularViewModel) {

    fun getListofPopular() {

        val apiClient = Network.getRetrofitInstance().create(ApiClient::class.java)
        val call = apiClient.getPopular("f93f2306dd57a8d5c1932faa0774cd16")

        call.enqueue(callback)
    }
}


