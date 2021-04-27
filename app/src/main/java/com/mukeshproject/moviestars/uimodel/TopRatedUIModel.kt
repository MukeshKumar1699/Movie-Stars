package com.mukeshproject.moviestars.uimodel

import com.mukeshproject.moviestars.network.popular.ResultsItem

sealed class TopRatedUIModel {

    data class Success(val TopRatedList: List<ResultsItem>) : TopRatedUIModel()

    data class Failure(val error: String) : TopRatedUIModel()
}