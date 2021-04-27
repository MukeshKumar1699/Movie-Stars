package com.mukeshproject.moviestars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.databinding.ItemLayoutBinding
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.viewholder.WishListViewHolder

class WishListAdapter(
    private var wishList: List<WishList>,
    private var itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<WishListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val itemLayoutBinding =
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishListViewHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        val wishList = wishList[position]
        holder.setData(wishList, itemClickListener)
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    fun updateData(wishList: List<WishList>) {
        this.wishList = wishList
        notifyDataSetChanged()
    }
}