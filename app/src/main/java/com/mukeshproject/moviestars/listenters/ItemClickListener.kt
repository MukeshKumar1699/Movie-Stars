package com.mukeshproject.moviestars.listenters

import com.mukeshproject.moviestars.network.popular.ResultsItem


interface ItemClickListener {

    fun onItemClicked(position: Int, results: ResultsItem)

    fun addToWishList(data: Any, isAdded: Boolean, list: String)

}