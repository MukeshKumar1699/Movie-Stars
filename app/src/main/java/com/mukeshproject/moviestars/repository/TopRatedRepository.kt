package com.mukeshproject.moviestars.repository

import com.mukeshproject.moviestars.network.ApiClient
import com.mukeshproject.moviestars.network.Network
import com.mukeshproject.moviestars.viewmodel.TopRatedViewModel

class TopRatedRepository(private val callback: TopRatedViewModel) {

    fun getListofTopRated() {

        val apiClient = Network.getRetrofitInstance().create(ApiClient::class.java)
        val call = apiClient.getTopRated("f93f2306dd57a8d5c1932faa0774cd16")

        call.enqueue(callback)
    }
}