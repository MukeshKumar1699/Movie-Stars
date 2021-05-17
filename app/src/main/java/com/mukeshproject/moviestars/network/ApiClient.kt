package com.mukeshproject.moviestars.network

import com.mukeshproject.moviestars.network.movievideo.ResponseMovieVideo
import com.mukeshproject.moviestars.network.popular.ResponseTrending
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("/3/movie/popular")
    fun getPopular(
        @Query("api_key") key: String,
        @Query("page") page: Int?): Call<ResponseTrending>

    @GET("/3/movie/top_rated")
    fun getTopRated(
        @Query("api_key") key: String,
        @Query("page") page: Int?): Call<ResponseTrending>

    @GET("/3/search/movie")
    fun getSearch(
        @Query("api_key") key: String,
        @Query("query") query: String
    ): Call<ResponseTrending>

    @GET("/3/movie/{movie}/videos")
    fun getTrailer(
        @Path("movie") movieId: Int,
        @Query("api_key") query: String
    ): Call<ResponseMovieVideo>
}