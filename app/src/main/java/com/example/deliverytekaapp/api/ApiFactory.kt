package com.example.deliverytekaapp.api

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiFactory {
    private const val BASE_URL = "https://komaroff-site.000webhostapp.com/"

    private val retrofit  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

   val apiService = retrofit.create(ApiService::class.java)
}