package com.mukeshproject.moviestars.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import kotlinx.android.synthetic.main.item_layout.view.*

class SearchViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun setData(results: ResultsItem, itemClickListener: ItemClickListener) {

        view.apply {
            Glide.with(iv_movieImage).load("https://image.tmdb.org/t/p/w500/" + results.posterPath)
                .into(iv_movieImage)
//            tv_movieName.text = results.title
//            tv_releaseDate.text = results.releaseDate
            //val voteCount : Int = results.voteAverage as Int

            //progressBar.secondaryProgress = voteCount
            //txtProgress.text = results.voteAverage as CharSequence?

            iv_movieImage.setOnClickListener {
                itemClickListener.onItemClicked(adapterPosition, results)
            }
        }

    }
}