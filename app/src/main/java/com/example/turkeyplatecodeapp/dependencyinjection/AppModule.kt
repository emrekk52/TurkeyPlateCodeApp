package com.example.cryptolistapp.dependencyinjection

import com.example.cryptolistapp.repository.CityRepository
import com.example.cryptolistapp.service.CityAPI
import com.example.turkeyplatecodeapp.constants.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideCityRepository(
        api: CityAPI) = CityRepository(api)


    @Singleton
    @Provides
    fun provideCityApi(): CityAPI {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CityAPI::class.java)
    }




}
