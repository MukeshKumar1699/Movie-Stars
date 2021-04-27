package com.mukeshproject.moviestars.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.databinding.ItemLayoutBinding
import com.mukeshproject.moviestars.listenters.ItemClickListener

class WishListViewHolder(private val view: ItemLayoutBinding) :
    RecyclerView.ViewHolder(view.root) {

    fun setData(results: WishList, itemClickListener: ItemClickListener) {


        view.apply {
            Glide.with(view.ivMovieImage)
                .load("https://image.tmdb.org/t/p/original/" + results.image)
                .into(view.ivMovieImage)

            view.addToWishlist.setImageResource(R.drawable.ic_delete)


            view.addToWishlist.setOnClickListener {

                view.addToWishlist.setImageResource(R.drawable.ic_bookmark_add)
                itemClickListener.addToWishList(results, true, "wishlist")

            }
        }

    }
}