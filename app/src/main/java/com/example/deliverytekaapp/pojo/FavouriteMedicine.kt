package com.example.deliverytekaapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_medicine")
data class FavouriteMedicine(
    @PrimaryKey
    val userId:String,
    val medicineId:String,
    val count:Int
)


