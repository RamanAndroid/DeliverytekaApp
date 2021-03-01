package com.example.deliverytekaapp.api

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

 object ApiFactory {
    const val BASE_URL = "https://komaroff-site.000webhostapp.com/"


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .addConverterFactory(ApiWorker.gsonConverter)
            .client(ApiWorker.client)
            .build()

        val apiService = retrofit.create(ApiService::class.java)
}
