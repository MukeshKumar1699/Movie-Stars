package com.mukeshproject.moviestars.uimodel

import com.mukeshproject.moviestars.network.popular.ResultsItem

sealed class PopularUIModel {

    data class Success(val TrendingList: List<ResultsItem>) : PopularUIModel()

    data class Failure(val error: String) : PopularUIModel()
}
