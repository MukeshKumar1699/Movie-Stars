package com.mukeshproject.moviestars.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Wishlist_DB")
data class WishList(

    @PrimaryKey(autoGenerate = true)
    var wishListId: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "image")
    val image: String
)
