package com.mukeshproject.moviestars.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Network {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"

        /*
    This httpLoggingInterceptor is used to get the Api request logs, how the Api looks like, whats the
    status code, and the response.
     */
        private val httpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        /*
        This method gives the retrofit instance

         */
        fun getRetrofitInstance(): Retrofit {
            val builder = Retrofit.Builder()
                .baseUrl(BASE_URL) //In most cases requests to a server, and the responses from the server, are not Java objects. They’re mapped to some language neutral format like JSON
                .addConverterFactory(GsonConverterFactory.create()) //this client will help you to print the API call logs in the Logcat
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(httpLoggingInterceptor)
                        .build()
                )
            return builder.build()
        }
    }

}

