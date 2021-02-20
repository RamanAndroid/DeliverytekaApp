package com.example.deliverytekaapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.deliverytekaapp.pojo.Medicine

@Dao
interface MedicinePriceInfoDao {
    @Query("SELECT * FROM full_price_list")
    fun getMedicine(): LiveData<List<Medicine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicineList(priceList: List<Medicine>)
/*
    @Query("SELECT * FROM favourite_medicine")
    fun getFavouriteMedicine(): LiveData<List<Medicine>>

    @Query("SELECT * FROM favourite_medicine WHERE id==:medicineId")
    suspend fun getFavouriteMedicineId(medicineId:Int):FavouriteMedicine

    @Insert
    suspend fun insertFavouriteMedicine(favouriteMedicine: List<FavouriteMedicine>)

    @Delete
    suspend fun deleteFavouriteMedicine(favouriteMedicine: List<FavouriteMedicine>)

 */

}