package com.mukeshproject.moviestars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mukeshproject.moviestars.databinding.ItemLayoutBinding
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.viewholder.PopularViewHolder

class PopularAdapter(
    var trendingList: List<ResultsItem>,
    var itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {

        val itemLayoutBinding =
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {

        val resultsItem = trendingList[position]
        holder.setData(resultsItem, itemClickListener)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    fun updateDataList(trendingList: List<ResultsItem>) {
        this.trendingList = trendingList
        notifyDataSetChanged()
    }
}