package com.mukeshproject.moviestars.repository

import com.mukeshproject.moviestars.network.ApiClient
import com.mukeshproject.moviestars.network.Network
import com.mukeshproject.moviestars.network.movievideo.ResponseMovieVideo
import retrofit2.Callback

class MovieVideoRepository(private val callback: Callback<ResponseMovieVideo>) {

    fun ListofTrailer(movieId: Int) {

        val apiClient = Network.getRetrofitInstance().create(ApiClient::class.java)
        val call = apiClient.getTrailer(movieId, "f93f2306dd57a8d5c1932faa0774cd16")

        call.enqueue(callback)
    }

}
