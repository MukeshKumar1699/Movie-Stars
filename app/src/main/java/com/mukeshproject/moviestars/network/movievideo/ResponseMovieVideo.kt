package com.mukeshproject.moviestars.network.movievideo

import com.google.gson.annotations.SerializedName

data class ResponseMovieVideo(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null
)