package com.octacore.agromallfarms.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.octacore.agromallfarms.data.dao.FarmerDao
import com.octacore.agromallfarms.model.Farm
import com.octacore.agromallfarms.model.Farmer

@Database(entities = [Farmer::class, Farm::class], version = 1, exportSchema = false)
abstract class FarmersDatabase : RoomDatabase() {
    abstract fun farmerDao(): FarmerDao

    companion object {
        @Volatile
        private var INSTANCE: FarmersDatabase? = null

        fun getDatabase(context: Context): FarmersDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FarmersDatabase::class.java,
                    "farmers-db"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}