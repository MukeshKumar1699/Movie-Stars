package com.mukeshproject.moviestars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.viewholder.SearchViewHolder

class SearchAdapter(
    var searchList: List<ResultsItem>,
    var itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<SearchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val resultsItem = searchList[position]
        holder.setData(resultsItem, itemClickListener)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    fun updateDataList(searchList: List<ResultsItem>) {
        this.searchList = searchList
        notifyDataSetChanged()
    }


}