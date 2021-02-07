package com.mukeshproject.moviestars.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WishList::class], version = 1, exportSchema = false)
abstract class WishListDatabase : RoomDatabase() {

    abstract val wishListDao: WishListDao

    companion object {

        private var INSTANCE: WishListDatabase? = null

        fun getInstance(context: Context): WishListDatabase {
            synchronized(this) {

                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(context, WishListDatabase::class.java, "WishList_DB")
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}