package com.example.turkeyplatecodeapp.dao

import android.content.Context
import androidx.room.*
import com.example.turkeyplatecodeapp.model.Data
import com.example.turkeyplatecodeapp.model.IlcelerTypeConverter

@Database(entities = [Data::class], version = 1, exportSchema = false)
@TypeConverters(IlcelerTypeConverter::class)
abstract class DatabaseCreator : RoomDatabase() {

    abstract fun dao(): DatabaseDao

    companion object {


        private var instance: DatabaseCreator? = null

        fun getInstance(context: Context): DatabaseCreator {
            synchronized(this) {
                var _instance = instance
                if (_instance == null) {
                    _instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseCreator::class.java,
                        "city_data"
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()

                    instance = _instance
                }

                return _instance
            }

        }

    }


}