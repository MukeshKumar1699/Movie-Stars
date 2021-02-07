package com.mukeshproject.moviestars.uimodel

import com.mukeshproject.moviestars.network.popular.ResultsItem

sealed class SearchUIModel {

    data class Success(val SearchList: List<ResultsItem>) : SearchUIModel()

    data class Failure(val error: String) : SearchUIModel()
}