package com.example.cryptolistapp.repository

import com.example.cryptolistapp.service.CityAPI
import com.example.turkeyplatecodeapp.model.CityMapModel
import com.example.turkeyplatecodeapp.model.PlateModel
import com.example.turkeyplatecodeapp.service.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class CityRepository @Inject constructor(
    private val api: CityAPI
) {

    suspend fun getCity(): Resource<PlateModel> {
        val response = try {
            api.getCityList()

        } catch (e: Exception) {
            return Resource.Error("Error : ${e.message.toString()}")
        }
        return Resource.Success(response)
    }

    suspend fun getCityMap(): Resource<CityMapModel> {
        val response = try {
            api.getCityMapList()

        } catch (e: Exception) {
            return Resource.Error("Error : ${e.message.toString()}")
        }
        return Resource.Success(response)
    }




}