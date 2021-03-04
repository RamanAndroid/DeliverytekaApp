package com.example.deliverytekaapp.pojo

import androidx.room.Entity
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class MedicineCart(
    @SerializedName("id")
    @Expose
    val id: String?,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("price")
    @Expose
    val price: String?,

    @SerializedName("country")
    @Expose
    val country: String?,

    @SerializedName("pack")
    @Expose
    val pack: String?,

    @SerializedName("dosage")
    @Expose
    val dosage: String?,

    @SerializedName("form")
    @Expose
    val form: String?,

    @SerializedName("isrecipe")
    @Expose
    val isrecipe: String?,

    @SerializedName("description")
    @Expose
    val description: String?,

    @SerializedName("category")
    @Expose
    val category: String?,

    @SerializedName("img")
    @Expose
    val img: String?,

    @SerializedName("count")
    @Expose
    val count: String?
){

    fun getFullImageUrl(): String {
        return "https://komaroff-site.000webhostapp.com/$img"
    }
}