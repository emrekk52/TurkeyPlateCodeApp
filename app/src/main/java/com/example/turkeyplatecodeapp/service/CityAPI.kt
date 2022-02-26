package com.example.cryptolistapp.service

import com.example.turkeyplatecodeapp.model.CityMapModel
import com.example.turkeyplatecodeapp.model.PlateModel
import retrofit2.http.GET


interface CityAPI {

    @GET("snrylmz/il-ilce-json/master/js/il-ilce.json")
    suspend fun getCityList(): PlateModel

    @GET("emrekk52/map_cities/main/cities_map.json")
    suspend fun getCityMapList(): CityMapModel

}


