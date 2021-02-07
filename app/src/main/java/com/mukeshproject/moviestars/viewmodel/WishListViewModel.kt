package com.mukeshproject.moviestars.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.database.WishListDatabase

class WishListViewModel(private val context: Context) : ViewModel() {

    fun fetchDataFromDB(): LiveData<List<WishList>> {
        return WishListDatabase.getInstance(context)
            .wishListDao.getAllWishlist()
    }

    fun deleteWishList(wishList: WishList) {

        Thread {
            WishListDatabase.getInstance(context).wishListDao.deleleWishList(wishList)
        }.start()
    }
}