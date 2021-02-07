package com.mukeshproject.moviestars.listenters

import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.network.popular.ResultsItem


interface ItemClickListener {

    fun onItemClicked(position: Int, results: ResultsItem)

    fun onDeleteClicked(wishList: WishList)
}