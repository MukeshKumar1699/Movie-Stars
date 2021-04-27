package com.mukeshproject.moviestars.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.databinding.ItemLayoutBinding
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem

class TopRatedViewHolder(private val view: ItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {

    fun setData(results: ResultsItem, itemClickListener: ItemClickListener) {

        var isClicked = false

        view.apply {

            view.addToWishlist.setImageResource(R.drawable.ic_bookmark_add)
            Glide.with(view.ivMovieImage)
                .load("https://image.tmdb.org/t/p/original/" + results.posterPath)
                .into(ivMovieImage)

            view.addToWishlist.setOnClickListener {

                if (isClicked) {
                    isClicked = false
                    view.addToWishlist.setImageResource(R.drawable.ic_bookmark_add)
                    itemClickListener.addToWishList(results, true, "top-rated")
                } else {
                    isClicked = true
                    view.addToWishlist.setImageResource(R.drawable.ic_bookmark_added)
                    itemClickListener.addToWishList(results, false, "top-rated")

                }
            }
            view.ivMovieImage.setOnClickListener {
                itemClickListener.onItemClicked(adapterPosition, results)
            }
        }
    }
}