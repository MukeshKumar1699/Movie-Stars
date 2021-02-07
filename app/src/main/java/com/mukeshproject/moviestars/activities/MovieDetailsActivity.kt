package com.mukeshproject.moviestars.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mukeshproject.moviestars.R
import com.mukeshproject.moviestars.uimodel.MovieMediaUIModel
import com.mukeshproject.moviestars.viewmodel.MovieVideoViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.item_layout.view.*

class MovieDetailsActivity : AppCompatActivity() {

    private var movieId: Int = 0
    private var movieName = String()
    private var backDrop = String()
    private var poster = String()
    private var overview = String()
    var youtubekey: String? = null
    var URL = "http://www.youtube.com/watch?v="

    private lateinit var movieVideoViewModel: MovieVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        getIntentData()

        movieVideoViewModel = ViewModelProvider(this).get(MovieVideoViewModel::class.java)
        observeLiveData()

        movieVideoViewModel.callAPI(movieId)
        UpdateUI()
    }

    private fun UpdateUI() {

        iv_backMoviesDetails.setOnClickListener {

            val intent = Intent(this@MovieDetailsActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        Glide.with(iv_backdropMovieDetails).load("https://image.tmdb.org/t/p/original/" + backDrop)
            .into(iv_backdropMovieDetails)

        Glide.with(iv_posterMovieDetails).load("https://image.tmdb.org/t/p/original/" + poster)
            .into(iv_posterMovieDetails)

        movieNameMovieDetails.text = movieName
        overViewMoviesDetails.text = overview
        btn_playTrailer.setOnClickListener {

            //Toast.makeText(applicationContext, URL+youtubekey, Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(URL + youtubekey)
                )
            )
        }
    }

    private fun observeLiveData() {

        movieVideoViewModel.liveData.observe(this, {
            when (it) {

                is MovieMediaUIModel.Success -> {
                    youtubekey = it.VideoList[0].key
                }

                is MovieMediaUIModel.Failure -> {


                }
            }
        })
    }

    private fun getIntentData() {

        movieId = intent.getIntExtra("movieId", 0)
        movieName = intent.getStringExtra("movieName").toString()
        backDrop = intent.getStringExtra("backdropImage").toString()
        poster = intent.getStringExtra("posterImage").toString()
        overview = intent.getStringExtra("overview").toString()

//
    }
}