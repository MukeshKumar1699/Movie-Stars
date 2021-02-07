package com.mukeshproject.moviestars.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WishListDao {

    @Insert
    suspend fun insertWishList(wishList: WishList)

    @Query("Select * From Wishlist_DB")
    fun getAllWishlist(): LiveData<List<WishList>>

    @Delete
    fun deleleWishList(wishList: WishList)
}
