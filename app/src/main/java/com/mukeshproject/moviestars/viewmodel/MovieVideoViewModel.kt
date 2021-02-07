package com.mukeshproject.moviestars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mukeshproject.moviestars.network.movievideo.ResponseMovieVideo
import com.mukeshproject.moviestars.network.movievideo.ResultsItem
import com.mukeshproject.moviestars.repository.MovieVideoRepository
import com.mukeshproject.moviestars.uimodel.MovieMediaUIModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieVideoViewModel : ViewModel(), Callback<ResponseMovieVideo> {

    private val repository = MovieVideoRepository(this)

    private val mutableLiveData = MutableLiveData<MovieMediaUIModel>()

    val liveData: LiveData<MovieMediaUIModel> = mutableLiveData


    override fun onResponse(
        call: Call<ResponseMovieVideo>,
        response: Response<ResponseMovieVideo>
    ) {

        response.body()?.let {

            mutableLiveData.value = MovieMediaUIModel.Success(it.results as List<ResultsItem>)
        }
    }

    override fun onFailure(call: Call<ResponseMovieVideo>, t: Throwable) {
        mutableLiveData.value = MovieMediaUIModel.Failure(t.message!!)
    }

    fun callAPI(movieId: Int) {
        repository.ListofTrailer(movieId)
    }
}