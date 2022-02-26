package com.example.turkeyplatecodeapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.turkeyplatecodeapp.model.CityMap
import com.example.turkeyplatecodeapp.model.Data
import com.example.turkeyplatecodeapp.model.Ilceler

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM CITY_DATA")
    fun getAllData(): LiveData<List<Data>>

    @Query("SELECT * FROM CITY_DATA")
    fun getDataAll(): List<Data>

    @Query("SELECT COUNT(*) FROM CITY_DATA")
    fun getAllDataSize(): Long

    @Query("SELECT * FROM CITY_DATA WHERE id=:mId")
    fun getByIdToCity(mId: String): Data

    @Insert
    suspend fun addData(item: Data)

    @Delete
    suspend fun delete(item: Data)

    @Query("DELETE FROM CITY_DATA")
    suspend fun deleteAllData()

    @Query("SELECT * FROM CITY_DATA ORDER BY plaka_kodu ASC")
    fun getOrderPlateASC(): List<Data>

    @Query("SELECT * FROM CITY_DATA ORDER BY plaka_kodu DESC")
    fun getOrderPlateDESC(): List<Data>





    @Query("SELECT * FROM CITY_DATA ORDER BY il_adi ASC")
    fun getOrderCityASC(): List<Data>

    @Query("SELECT * FROM CITY_DATA ORDER BY il_adi DESC")
    fun getOrderCityDESC(): List<Data>


    @Query("SELECT * FROM CITY_DATA ORDER BY gercek_nufus ASC")
    fun getOrderPopulationASC(): List<Data>

    @Query("SELECT * FROM CITY_DATA ORDER BY gercek_nufus DESC")
    fun getOrderPopulationDESC(): List<Data>


    @Query("SELECT * FROM CITY_DATA WHERE isFavorite=1 ORDER BY alan_kodu ASC")
    fun getMyFav(): List<Data>

    @Query("SELECT * FROM CITY_DATA ORDER BY alan_kodu ASC")
    fun getOrderPhoneCodeASC(): List<Data>

    @Query("SELECT * FROM CITY_DATA ORDER BY alan_kodu DESC")
    fun getOrderPhoneCodeDESC(): List<Data>


    @Query("UPDATE CITY_DATA SET isFavorite=:isFav WHERE id=:mId ")
    suspend fun updateFavorite(mId: Int, isFav: Boolean)

    @Query("SELECT * FROM CITY_DATA ORDER BY RANDOM() LIMIT 10")
    fun getQuestionLimit10(): List<Data>

    @Query("SELECT DISTINCT il_adi FROM CITY_DATA WHERE il_adi NOT LIKE '%' || :parameter || '%' ORDER BY RANDOM() LIMIT 3")
    fun getAnswerList(parameter: String): MutableList<String>

    @Query("SELECT DISTINCT plaka_kodu FROM CITY_DATA WHERE plaka_kodu NOT LIKE '%' || :parameter || '%' ORDER BY RANDOM() LIMIT 3")
    fun getPlateList(parameter: String): MutableList<String>

    @Query("SELECT * FROM CITY_DATA WHERE il_adi=:parameter")
    fun getIlceler(parameter: String): Data

}

