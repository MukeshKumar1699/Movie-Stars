package com.mukeshproject.moviestars.database

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mukeshproject.moviestars.viewmodel.WishListViewModel

class WishListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(WishListViewModel::class.java)) {
            return WishListViewModel(context) as T

        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}