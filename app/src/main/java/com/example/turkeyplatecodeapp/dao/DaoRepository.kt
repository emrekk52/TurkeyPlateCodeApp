package com.example.turkeyplatecodeapp.dao

import androidx.lifecycle.LiveData
import com.example.turkeyplatecodeapp.model.CityMap
import com.example.turkeyplatecodeapp.model.Data

class DaoRepository(private val dataDao: DatabaseDao) {


    // val readAllData: LiveData<List<Data>> = dataDao.getAllData()


    fun getSize() = dataDao.getAllDataSize()

    suspend fun addData(item: Data) {
        dataDao.addData(item)
    }


    fun getByIdToCity(id: String) = dataDao.getByIdToCity(id)

    suspend fun updateFavorite(mId: Int, isFav: Boolean) {
        dataDao.updateFavorite(mId, isFav)
    }


    suspend fun deleteData(item: Data) {
        dataDao.delete(item)
    }

    suspend fun deleteAllData() {
        dataDao.deleteAllData()
    }

    fun getOrderPhoneCodeASC() = dataDao.getOrderPhoneCodeASC()
    fun getOrderPhoneCodeDESC() = dataDao.getOrderPhoneCodeDESC()

    fun getOrderPlateASC() = dataDao.getOrderPlateASC()
    fun getOrderPlateDESC() = dataDao.getOrderPlateDESC()

    fun getOrderCityASC() = dataDao.getOrderCityASC()
    fun getOrderCityDESC() = dataDao.getOrderCityDESC()

    fun getOrderPopulationASC() = dataDao.getOrderPopulationASC()
    fun getOrderPopulationDESC() = dataDao.getOrderPopulationDESC()
    fun getMyFav() = dataDao.getMyFav()

    fun getDataAll() = dataDao.getDataAll()
    fun getIlceler(parameter: String) = dataDao.getIlceler(parameter)


    fun getQuestionLimit10() = dataDao.getQuestionLimit10()

    fun getAnswerList(parameter: String) = dataDao.getAnswerList(parameter)
    fun getPlateList(parameter: String) = dataDao.getPlateList(parameter)


}