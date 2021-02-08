package com.mukeshproject.moviestars.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import kotlinx.android.synthetic.main.item_layout.view.*

class PopularViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun setData(results: ResultsItem, itemClickListener: ItemClickListener) {

        var isClicked = false

        view.apply {

            addToWishlist.setImageResource(R.drawable.ic_bookmark_add)
            Glide.with(iv_movieImage)
                .load("https://image.tmdb.org/t/p/original/" + results.posterPath)
                .into(iv_movieImage)

            addToWishlist.setOnClickListener {

                if (isClicked) {
                    isClicked = false
                    addToWishlist.setImageResource(R.drawable.ic_bookmark_add)
                    itemClickListener.addToWishList(results, true, "popular")
                } else {
                    isClicked = true
                    addToWishlist.setImageResource(R.drawable.ic_bookmark_added)
                    itemClickListener.addToWishList(results, false, "popular")

                }
            }
            iv_movieImage.setOnClickListener {
                itemClickListener.onItemClicked(adapterPosition, results)
            }
        }

    }
}

