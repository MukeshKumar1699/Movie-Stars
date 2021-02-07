package com.mukeshproject.moviestars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mukeshproject.moviestars.network.popular.ResponseTrending
import com.mukeshproject.moviestars.network.popular.ResultsItem
import com.mukeshproject.moviestars.repository.PopularRepository
import com.mukeshproject.moviestars.uimodel.PopularUIModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularViewModel : ViewModel(), Callback<ResponseTrending> {

    private val repository = PopularRepository(this)

    private val mutableLiveData = MutableLiveData<PopularUIModel>()

    val liveData: LiveData<PopularUIModel> = mutableLiveData


    override fun onResponse(call: Call<ResponseTrending>, response: Response<ResponseTrending>) {

        response.body()?.let {

            mutableLiveData.value = PopularUIModel.Success(it.results as List<ResultsItem>)
        }
    }

    override fun onFailure(call: Call<ResponseTrending>, t: Throwable) {
        mutableLiveData.value = PopularUIModel.Failure(t.message!!)
    }

    fun callAPI() {
        repository.getListofPopular()
    }

}

