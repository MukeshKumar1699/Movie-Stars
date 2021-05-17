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
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.activities.MovieDetailsActivity
import com.mukeshproject.moviestars.adapter.SearchAdapter
import com.mukeshproject.moviestars.database.WishList
import com.mukeshproject.moviestars.databinding.FragmentSearchBinding
import com.mukeshproject.moviestars.listenters.ItemClickListener
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.uimodel.SearchUIModel
import com.mukeshproject.moviestars.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment(), ItemClickListener {

    private lateinit var searchBinding: FragmentSearchBinding

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private val searchlist = emptyList<ResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        searchBinding = FragmentSearchBinding.inflate(layoutInflater)
        return searchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeLiveData()

    }

    private fun init() {

        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        searchBinding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }
            override fun onQueryTextChange(p0: String): Boolean {

                searchViewModel.callAPI(p0)
                Glide.with(progressSearch)
                    .load(R.raw.searching)
                    .into(progressSearch)
                searchBinding.progressSearch.visibility = View.VISIBLE

                return false
            }
        })

        setRecyclerAdapter()

    }

    private fun observeLiveData() {
        searchViewModel.liveData.observe(requireActivity(), {
            when (it) {

                is SearchUIModel.Success -> {
                    searchAdapter.updateDataList(it.SearchList)

                }

                is SearchUIModel.Failure -> {
                    Toast.makeText(
                        context,
                        "Error message ${it.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            searchBinding.progressSearch.visibility = View.GONE

        })
    }

    private fun setRecyclerAdapter() {

        searchAdapter = SearchAdapter(searchlist, this)
        val layoutManager = GridLayoutManager(context, 3)

        searchBinding.recyclerViewSearch.apply {
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