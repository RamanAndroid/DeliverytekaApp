package com.example.deliverytekaapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.deliverytekaapp.api.ApiFactory
import com.example.deliverytekaapp.database.AppDatabase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MedicineViewModel(application: Application) : AndroidViewModel(application) {
    private val db= AppDatabase.getInstance(application)
    val medicine =db.medicineInfoDao().getMedicine()
    //val favouriteMedicine =db.medicineInfoDao().getFavouriteMedicine()
    private val compositeDisposable = CompositeDisposable()

    init {
        loadDataMedicine()
    }

    private fun loadDataMedicine() {
        val disposable = ApiFactory.apiService.getListMedicine()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.medicineInfoDao().insertMedicineList(it)
            }, {
                Log.d("TEST_OF_LOADING_DATA", it.toString())
            })
        compositeDisposable.add(disposable)
    }
/*
    suspend fun insertFavouriteMedicine(medicine: List<FavouriteMedicine>) {
        db.medicineInfoDao().insertFavouriteMedicine(medicine)
    }

    suspend fun deleteFavouriteMovie(medicine: List<FavouriteMedicine>) {
        db.medicineInfoDao().insertFavouriteMedicine(medicine)
    }

 */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}