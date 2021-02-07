package com.mukeshproject.moviestars.network.popular

import com.google.gson.annotations.SerializedName

data class ResponseTrending(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("results")
    val results: MutableList<ResultsItem?>? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)