package com.mukeshproject.moviestars.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.databinding.ItemLayoutBinding
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem

class SearchViewHolder(private val view: ItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {

    fun setData(results: ResultsItem, itemClickListener: ItemClickListener) {

        var isClicked = false
        view.apply {

            view.addToWishlist.setImageResource(R.drawable.ic_bookmark_add)
            Glide.with(view.ivMovieImage)
                .load("https://image.tmdb.org/t/p/w500/" + results.posterPath)
                .into(view.ivMovieImage)

            view.ivMovieImage.setOnClickListener {
                itemClickListener.onItemClicked(adapterPosition, results)
            }

            view.addToWishlist.setOnClickListener {

                if (isClicked) {
                    isClicked = false
                    view.addToWishlist.setImageResource(R.drawable.ic_bookmark_add)
                    itemClickListener.addToWishList(results, true, "search")
                } else {
                    isClicked = true
                    view.addToWishlist.setImageResource(R.drawable.ic_bookmark_added)
                    itemClickListener.addToWishList(results, false, "search")

                }
            }
        }

    }
}