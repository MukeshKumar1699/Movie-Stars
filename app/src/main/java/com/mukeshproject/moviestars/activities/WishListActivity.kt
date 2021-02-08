package com.mukeshproject.moviestars.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.adapter.WishListAdapter
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.database.WishListViewModelFactory
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.viewmodel.WishListViewModel
import kotlinx.android.synthetic.main.activity_wish_list.*

class WishListActivity : AppCompatActivity(), ItemClickListener {

    private var wishList = emptyList<WishList>()
    private lateinit var wishListAdapter: WishListAdapter
    private lateinit var wishListViewModel: WishListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)

        back()
        wishListViewModel = WishListViewModelFactory(this).create(WishListViewModel::class.java)

        wishListViewModel.fetchDataFromDB().observe(this, {
            it?.let {
                this@WishListActivity.wishList = it
                wishListAdapter.updateData(wishList)
            }
        })
        setRecyclerAdapter()
    }

    private fun back() {

        iv_backWishlist.setOnClickListener {
            val intent = Intent(this@WishListActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRecyclerAdapter() {

        wishListAdapter = WishListAdapter(wishList, this)
        var layoutManager =
            GridLayoutManager(this@WishListActivity, 2, RecyclerView.VERTICAL, false)

        recyclerViewWishList.layoutManager = layoutManager
        recyclerViewWishList.adapter = wishListAdapter
    }

    override fun onItemClicked(position: Int, results: ResultsItem) {
        val intent = Intent(this@WishListActivity, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", results.id)
        startActivity(intent)
    }

    override fun addToWishList(data: Any, isAdded: Boolean, list: String) {
        wishListViewModel.deleteFromDatabase(data as WishList)
    }

}