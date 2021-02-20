package com.example.deliverytekaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deliverytekaapp.pojo.Medicine

@Database(entities = [Medicine::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        private var db: AppDatabase? = null
        private const val DB_NAME = "medicine.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration().build()
                db = instance
                return instance
            }
        }
    }

    abstract fun medicineInfoDao(): MedicinePriceInfoDao
}
//,FavouriteMedicine::class