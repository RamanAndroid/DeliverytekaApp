package com.example.deliverytekaapp.api

import com.example.deliverytekaapp.pojo.Medicine
import com.example.deliverytekaapp.pojo.MedicineCart
import com.example.deliverytekaapp.pojo.ShortUserInfo
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @GET("getmedicine.php")
    fun getListMedicine(): Single<List<Medicine>>

    @Headers("Content-Type: application/json")
    @GET("getmedicinebyid.php")
    fun getInfoAboutMedicine(
        @Query("id") id: String
    ): Single<List<Medicine>>

    @Headers("Content-Type: application/json")
    @GET("signup.php")
    fun signUpUser(
        @Query("id") id: String,
        @Query("phone") phone: String,
        @Query("password") password: String,
    ): Single<List<ShortUserInfo>>

    @Headers("Content-Type: application/json")
    @GET("login.php")
    fun loginUser(
        @Query("phone") phone: String,
        @Query("password") password: String,
    ): Single<List<ShortUserInfo>>

    @Headers("Content-Type: application/json")
    @GET("addtobasket.php")
    fun addToCart(
        @Query("user_id") userId: String?,
        @Query("medicine_id") medicineId: String?,
        @Query("count") count: String?,
    ): Single<List<Medicine>>

    @Headers("Content-Type: application/json")
    @GET("getbasketbyid.php")
    fun getInfoUserCart(
        @Query("id") id: String
    ): Single<List<MedicineCart>>

    @Headers("Content-Type: application/json")
    @GET(" getsuserbyid.php")
    fun getInfoUser(
        @Query("id") id: String
    ): Single<List<MedicineCart>>
}