package com.mukeshproject.moviestars.repository

import com.mukeshproject.moviestars.network.ApiClient
import com.mukeshproject.moviestars.network.Network
import com.mukeshproject.moviestars.network.popular.ResponseTrending
import retrofit2.Callback

class SearchRepository(private val callback: Callback<ResponseTrending>) {

    fun getListofSearch(query: String) {

        val apiClient = Network.getRetrofitInstance().create(ApiClient::class.java)
        val call = apiClient.getSearch("f93f2306dd57a8d5c1932faa0774cd16", query)

        call.enqueue(callback)
    }
}