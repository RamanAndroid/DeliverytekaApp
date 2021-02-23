package com.example.deliverytekaapp.api

import com.example.deliverytekaapp.pojo.FavouriteMedicine
import com.example.deliverytekaapp.pojo.Medicine
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("getmedicine.php")
    fun getListMedicine(): Single<List<Medicine>>

    @GET("getmedicinebyid.php")
    fun getInfoAboutMedicine(
        @Query("id") id: String
    ): Single<List<FavouriteMedicine>>
}