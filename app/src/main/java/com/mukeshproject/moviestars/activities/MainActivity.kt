package com.mukeshproject.moviestars.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.adapter.PopularAdapter
import com.mukeshproject.moviestars.adapter.TopRatedAdapter
import com.mukeshproject.moviestars.adapter.TopRatedUIModel
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.fragments.SearchFragment
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.uimodel.PopularUIModel
import com.mukeshproject.moviestars.viewmodel.PopularViewModel
import com.mukeshproject.moviestars.viewmodel.TopRatedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_layout.view.*


class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var popularViewModel: PopularViewModel
    private lateinit var topRatedViewModel: TopRatedViewModel

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter

    private val popularList = emptyList<ResultsItem>()
    private val topRatedList = emptyList<ResultsItem>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkInternet(applicationContext)) {
                showInternetRequired()
            }
        }
        search()
        wishList()
        user()
        popularViewModel = ViewModelProvider(this).get(PopularViewModel::class.java)
        topRatedViewModel = ViewModelProvider(this).get(TopRatedViewModel::class.java)

        setRecyclerAdapter()
        observePopularLiveData()

        if (savedInstanceState != null) {
        } else {
            /*
            This value will be displayed initially when the savedInstanceState is null
             */
        }
        Glide.with(progressMain)
            .load(R.raw.searching)
            .into(progressMain)
        progressMain.visibility = View.VISIBLE
        popularViewModel.callAPI()
        topRatedViewModel.callAPI()


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showInternetRequired() {

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Internet Is Required")
            .setCancelable(false)
            .setNegativeButton(
                "OK"
            ) { dialog, id ->
                val intent = Intent(this@MainActivity, WishListActivity::class.java)
                startActivity(intent)
            }

        val alert = alertDialogBuilder.create()
        alert.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false

    }

    private fun user() {

    }

    private fun wishList() {

        ib_wishList.setOnClickListener {
            val intent = Intent(this@MainActivity, WishListActivity::class.java)
            startActivity(intent)
        }
    }


    private fun search() {

        ib_search.setOnClickListener {

            val searchFragment = SearchFragment()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.flcontainer, searchFragment, "searchFragment")
                .addToBackStack("searchFragment")
                .commit()

        }

    }


    private fun observePopularLiveData() {
        popularViewModel.liveData.observe(this, {
            when (it) {

                is PopularUIModel.Success -> {
                    popularAdapter.updateDataList(it.TrendingList)
                    progressMain.visibility = View.GONE
                }

                is PopularUIModel.Failure -> {
                    progressMain.visibility = View.GONE
                }
            }
        })
        topRatedViewModel.liveData.observe(this, {
            when (it) {

                is TopRatedUIModel.Success -> {
                    progressMain.visibility = View.GONE

                    topRatedAdapter.updateDataList(it.TopRatedList)
                }
                is TopRatedUIModel.Failure -> {
                    progressMain.visibility = View.GONE

                }
            }
        })
    }

    /**
     * Sets the recycler view adapter
     */
    private fun setRecyclerAdapter() {

        popularAdapter = PopularAdapter(popularList, this)
        var layoutManager = GridLayoutManager(this@MainActivity, 1, RecyclerView.HORIZONTAL, false)

        recyclerViewPopular.apply {
            adapter = popularAdapter
            this.layoutManager = layoutManager

        }

        topRatedAdapter = TopRatedAdapter(topRatedList, this)
        layoutManager = GridLayoutManager(this@MainActivity, 1, RecyclerView.HORIZONTAL, false)

        recyclerViewTopRated.apply {
            adapter = topRatedAdapter
            this.layoutManager = layoutManager
        }
    }

    override fun onItemClicked(position: Int, results: ResultsItem) {

        val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)

        intent.putExtra("movieId", results.id)
        intent.putExtra("movieName", results.title)
        intent.putExtra("rating", results.voteAverage.toString())
        intent.putExtra("backdropImage", results.backdropPath)
        intent.putExtra("posterImage", results.posterPath)
        intent.putExtra("overview", results.overview)

        startActivity(intent)
    }


    override fun addToWishList(data: Any, isAdded: Boolean, list: String) {

        if (list == "popular") {
            if (isAdded) {
                popularViewModel.deleteFromDatabase(data as WishList, this)
            } else {
                popularViewModel.insertToDatabase(data as ResultsItem, this)

            }
        }
        if (list == "top-rated") {
            if (isAdded) {
                topRatedViewModel.deleteFromDatabase(data as WishList, this)
            } else {
                topRatedViewModel.insertToDatabase(data as ResultsItem, this)

            }
        }

    }


}





