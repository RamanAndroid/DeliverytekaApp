package com.example.deliverytekaapp.api

import com.example.deliverytekaapp.pojo.Medicine
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("getmedicine.php")
    fun getListMedicine(): Single<List<Medicine>>
}