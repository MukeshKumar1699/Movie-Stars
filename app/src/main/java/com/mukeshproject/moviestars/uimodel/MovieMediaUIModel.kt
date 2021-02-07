package com.mukeshproject.moviestars.uimodel

import com.mukeshproject.moviestars.network.movievideo.ResultsItem


sealed class MovieMediaUIModel {

    data class Success(val VideoList: List<ResultsItem>) : MovieMediaUIModel()

    data class Failure(val error: String) : MovieMediaUIModel()
}