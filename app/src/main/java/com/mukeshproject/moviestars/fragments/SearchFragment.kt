package com.mukeshproject.moviestars.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.activities.MovieDetailsActivity
import com.mukeshproject.moviestars.adapter.SearchAdapter
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.uimodel.SearchUIModel
import com.mukeshproject.moviestars.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private lateinit var searchViewModel: SearchViewModel
private lateinit var searchAdapter: SearchAdapter
private val searchlist = emptyList<ResultsItem>()

class SearchFragment : Fragment(), ItemClickListener {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sv_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(p0: String): Boolean {

                searchViewModel.callAPI(p0)
                Glide.with(progressSearch)
                    .load(R.raw.searching)
                    .into(progressSearch)
                progressSearch.visibility = View.VISIBLE

                return false
            }
        })
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        setRecyclerAdapter()
        observeLiveData()

    }

    private fun observeLiveData() {
        searchViewModel.liveData.observe(requireActivity(), {
            when (it) {

                is SearchUIModel.Success -> {
                    searchAdapter.updateDataList(it.SearchList)
                    progressSearch.visibility = View.GONE

                }

                is SearchUIModel.Failure -> {
                    progressSearch.visibility = View.GONE

                    Toast.makeText(
                        context,
                        "Error message ${it.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setRecyclerAdapter() {

        searchAdapter = SearchAdapter(searchlist, this)
        val layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        recyclerViewSearch.apply {
            adapter = searchAdapter
            this.layoutManager = layoutManager

        }
    }

    override fun onItemClicked(position: Int, results: ResultsItem) {

        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra("movieId", results.id)
        intent.putExtra("movieName", results.title)
        intent.putExtra("rating", results.voteAverage.toString())
        intent.putExtra("backdropImage", results.backdropPath)
        intent.putExtra("posterImage", results.posterPath)
        intent.putExtra("overview", results.overview)
        startActivity(intent)
    }


    override fun addToWishList(data: Any, isAdded: Boolean, list: String) {

        if (isAdded) {
            searchViewModel.deleteFromDatabase(data as WishList, requireContext())
        } else {
            searchViewModel.insertToDatabase(data as ResultsItem, requireContext())

        }
    }


}