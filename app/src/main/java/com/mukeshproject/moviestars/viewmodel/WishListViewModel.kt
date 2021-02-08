package com.mukeshproject.moviestars.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.database.WishListDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class WishListViewModel(private val context: Context) : ViewModel() {

    fun fetchDataFromDB(): LiveData<List<WishList>> {
        return WishListDatabase.getInstance(context)
            .wishListDao.getAllWishlist()
    }

    fun deleteFromDatabase(wishList: WishList) {

        CoroutineScope(IO).launch {
            WishListDatabase.getInstance(context).wishListDao
                .deleleWishList(wishList)
        }
    }


}