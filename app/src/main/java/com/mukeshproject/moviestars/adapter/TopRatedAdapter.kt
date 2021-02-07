package com.mukeshproject.moviestars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.viewholder.TopRatedViewHolder

class TopRatedAdapter(
    var topRatedList: List<ResultsItem>,
    var itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<TopRatedViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return TopRatedViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        val resultsItem = topRatedList[position]
        holder.setData(resultsItem, itemClickListener)
    }

    override fun getItemCount(): Int {
        return topRatedList.size
    }

    fun updateDataList(searchList: List<ResultsItem>) {
        this.topRatedList = searchList
        notifyDataSetChanged()
    }


}