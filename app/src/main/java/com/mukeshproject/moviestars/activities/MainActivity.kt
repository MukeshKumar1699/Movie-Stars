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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.adapter.PopularAdapter
import com.mukeshproject.moviestars.adapter.TopRatedAdapter
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.databinding.ActivityMainBinding
import com.mukeshproject.moviestars.fragments.SearchFragment
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.uimodel.PopularUIModel
import com.mukeshproject.moviestars.uimodel.TopRatedUIModel
import com.mukeshproject.moviestars.viewmodel.PopularViewModel
import com.mukeshproject.moviestars.viewmodel.TopRatedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*


class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var popularViewModel: PopularViewModel
    private lateinit var topRatedViewModel: TopRatedViewModel

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter


    private val popularList = emptyList<ResultsItem>()
    private val topRatedList = emptyList<ResultsItem>()


    var popularPage = 1
    var topRatedPage = 1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        init()

        showLoadingProgress(View.VISIBLE)
        popularViewModel.callAPI(popularPage)
        topRatedViewModel.callAPI(topRatedPage)

        observePopularLiveData()
        observeTopRatedLiveData()

    }



    fun showLoadingProgress(visiblity: Int) {
        Glide.with(progressMain)
            .load(R.raw.searching)
            .into(progressMain)

        when (visiblity) {
            View.VISIBLE -> mainBinding.progressMain.visibility = View.VISIBLE
            View.INVISIBLE -> mainBinding.progressMain.visibility = View.INVISIBLE
            View.GONE -> mainBinding.progressMain.visibility = View.GONE
        }
    }

    private fun init() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkInternet(applicationContext)) {
                showInternetRequired()
            }
        }

        popularViewModel = ViewModelProvider(this).get(PopularViewModel::class.java)
        topRatedViewModel = ViewModelProvider(this).get(TopRatedViewModel::class.java)

        search()
        wishList()
        setRecyclerAdapterPopular()
        setRecyclerAdapterTopRated()
        initScrollListenerPopular()
        initScrollListenerTopRated()

    }



    private fun initScrollListenerPopular() {

        recyclerViewPopular.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                    if (linearLayoutManager != null && linearLayoutManager.findLastVisibleItemPosition() == popularAdapter.itemCount - 1) {
                        //bottom of list!
                            showLoadingProgress(View.VISIBLE)
                            popularViewModel.callAPI(popularPage)

                    }

            }
        })
    }

    private fun initScrollListenerTopRated() {

        recyclerViewTopRated.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val LayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (LayoutManager != null && LayoutManager.findLastCompletelyVisibleItemPosition() == topRatedAdapter.itemCount - 1) {

                    showLoadingProgress(View.VISIBLE)
                        topRatedViewModel.callAPI(topRatedPage)
                }
            }
        })
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

    private fun wishList() {

        mainBinding.ibWishList.setOnClickListener {
            val intent = Intent(this@MainActivity, WishListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun search() {

        mainBinding.ibSearch.setOnClickListener {

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
                    popularPage++

                }
                is PopularUIModel.Failure -> {
                }
            }
            showLoadingProgress(View.GONE)
        })
    }

    private fun observeTopRatedLiveData() {
        topRatedViewModel.liveData.observe(this, {
            when (it) {

                is TopRatedUIModel.Success -> {
                    topRatedAdapter.updateDataList(it.TopRatedList)
                    topRatedPage++
                }
                is TopRatedUIModel.Failure -> {
                }
            }
            showLoadingProgress(View.GONE)
        })
    }

    private fun setRecyclerAdapterTopRated() {

        topRatedAdapter = TopRatedAdapter(topRatedList, this)
        val layoutManager = GridLayoutManager(this@MainActivity,3)

        mainBinding.recyclerViewTopRated.apply {
            adapter = topRatedAdapter
            this.layoutManager = layoutManager
        }

    }

    private fun setRecyclerAdapterPopular() {

        popularAdapter = PopularAdapter(popularList, this)
        val layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)

        mainBinding.recyclerViewPopular.apply {
            adapter = popularAdapter
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





