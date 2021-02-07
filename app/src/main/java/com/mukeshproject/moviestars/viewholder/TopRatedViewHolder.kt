package com.mukeshproject.moviestars.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import kotlinx.android.synthetic.main.item_layout.view.*

class TopRatedViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun setData(results: ResultsItem, itemClickListener: ItemClickListener) {

        var isClicked = false

        view.apply {
            Glide.with(iv_movieImage)
                .load("https://image.tmdb.org/t/p/original/" + results.posterPath)
                .into(iv_movieImage)

            addToWishlist.setOnClickListener {

                if (isClicked) {
                    addToWishlist.setImageResource(R.drawable.ic_bookmark_add)
                    isClicked = false

                    Thread {

                    }.start()

                } else {
                    addToWishlist.setImageResource(R.drawable.ic_bookmark_added)
                    isClicked = true
                }
            }
            iv_movieImage.setOnClickListener {
                itemClickListener.onItemClicked(adapterPosition, results)
            }
        }
    }
}