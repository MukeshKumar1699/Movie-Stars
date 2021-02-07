package com.mukeshproject.moviestars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mukeshproject.moviestars.network.popular.ResponseTrending
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.repository.SearchRepository
import com.mukeshproject.moviestars.uimodel.SearchUIModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel(), Callback<ResponseTrending> {

    private val repository = SearchRepository(this)

    private val mutableLiveData = MutableLiveData<SearchUIModel>()

    val liveData: LiveData<SearchUIModel> = mutableLiveData


    override fun onResponse(call: Call<ResponseTrending>, response: Response<ResponseTrending>) {

        response.body()?.let {
            mutableLiveData.value = SearchUIModel.Success(it.results as List<ResultsItem>)
        }
    }

    override fun onFailure(call: Call<ResponseTrending>, t: Throwable) {
        mutableLiveData.value = SearchUIModel.Failure(t.message!!)
    }

    fun callAPI(p0: String) {
        repository.getListofSearch(p0)
    }
}