package com.mukeshproject.moviestars.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.listenters.ItemClickListener
import kotlinx.android.synthetic.main.item_layout.view.*

class WishListViewHolder(private val view: View, var listener: ItemClickListener) :
    RecyclerView.ViewHolder(view) {

    fun setData(results: WishList, itemClickListener: ItemClickListener) {


        view.apply {
            Glide.with(iv_movieImage).load("https://image.tmdb.org/t/p/original/" + results.image)
                .into(iv_movieImage)

        }

    }
}