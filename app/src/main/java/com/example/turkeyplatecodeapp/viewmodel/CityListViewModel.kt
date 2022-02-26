package com.example.cryptolistapp.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.cryptolistapp.repository.CityRepository
import com.example.turkeyplatecodeapp.dao.DaoRepository
import com.example.turkeyplatecodeapp.dao.DatabaseCreator
import com.example.turkeyplatecodeapp.dao.DatabaseDao
import com.example.turkeyplatecodeapp.model.CityMap
import com.example.turkeyplatecodeapp.model.Data
import com.example.turkeyplatecodeapp.service.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CityListViewModel @Inject constructor(
    private val repository: CityRepository,
    application: Application
) : AndroidViewModel(application) {


    var cityList = mutableStateOf<List<Data>>(listOf())
    var cityMapList = mutableStateOf<List<CityMap>>(listOf())

    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCityList = listOf<Data>()

    private var isSearchStarting = true

    private val daoRepository: DaoRepository
    private val dao: DatabaseDao

    var result = mutableStateOf<List<Data>>(listOf())

    // var mCityList: LiveData<List<Data>>

    init {
        dao = DatabaseCreator.getInstance(application).dao()
        daoRepository = DaoRepository(dao)
        cityList.value = daoRepository.getDataAll()
        if (daoRepository.getSize() <= 0)
            loadCityList()
    }


    fun searchCityList(query: String) {
        val listToSearch = if (isSearchStarting)
            cityList.value
        else
            initialCityList


        viewModelScope.launch(Dispatchers.Default) {

            if (query.isEmpty()) {
                cityList.value = initialCityList
                isSearchStarting = true
                return@launch
            }

            result.value = listToSearch.filter {
                it.il_adi.contains(query.trim(), ignoreCase = true) or
                        it.plaka_kodu.contains(query.trim()) or
                        it.alan_kodu.contains(query.trim())
            }

            if (isSearchStarting) {
                initialCityList = cityList.value
                isSearchStarting = false
            }

            cityList.value = result.value

        }


    }


    fun loadCityList() {

        viewModelScope.launch {
            isLoading.value = true

            val result = repository.getCity()
            val cityMapResult = repository.getCityMap()


            when (cityMapResult) {

                is Resource.Success -> {
                    val cityMap = cityMapResult.data!!.data.mapIndexed { _, dt ->
                        CityMap(
                            dt.city_url,
                            dt.plate
                        )
                    }

                    errorMessage.value = ""
                    isLoading.value = false
                    cityMapList.value += cityMap


                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

            }


            when (result) {

                is Resource.Success -> {
                    val city = result.data!!.data.mapIndexed { _, dt ->
                        Data(
                            dt.alan_kodu,
                            dt.bolge,
                            dt.erkek_nufus,
                            dt.erkek_nufus_yuzde,
                            dt.il_adi,
                            dt.ilceler,
                            dt.kadin_nufus,
                            dt.kadin_nufus_yuzde,
                            dt.kisa_bilgi,
                            dt.nufus,
                            dt.nufus_yuzdesi_genel,
                            dt.plaka_kodu,
                            dt.yuzolcumu
                        ).also {
                            it.gercek_nufus = it.nufus.replace(".", "").toInt()
                            it.cityMapUrl =
                                cityMapList.value[it.plaka_kodu.toFloat().toInt() - 1].city_url
                        }

                    }

                    println()

                    errorMessage.value = ""
                    isLoading.value = false
                    cityList.value += city

                    addDatabase(cityList.value)

                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

            }


        }

    }

    private fun addDatabase(value: List<Data>) {
        viewModelScope.launch(Dispatchers.IO) {
            daoRepository.deleteAllData()
            value.forEach {
                daoRepository.addData(it)
            }
        }

    }

    fun updateFavorite(mId: Int, isFav: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            daoRepository.updateFavorite(mId, isFav)
            cityList.value = daoRepository.getDataAll()
        }
    }


    fun getFilterCity(idd: String) = daoRepository.getByIdToCity(idd)

    // order list to car plates
    fun orderList(id: Float) {
        viewModelScope.launch(Dispatchers.Default) {
            when (id) {
                //plaka
                0f -> {
                    cityList.value = daoRepository.getOrderPlateDESC()
                }

                0.1f -> {
                    cityList.value = daoRepository.getOrderPlateASC()
                }
                //telefon kodu
                1f -> {
                    cityList.value = daoRepository.getOrderPhoneCodeDESC()
                }
                1.1f -> {
                    cityList.value = daoRepository.getOrderPhoneCodeASC()

                }
                //şehir adı
                2f -> {
                    cityList.value = daoRepository.getOrderCityDESC()

                }

                2.1f -> {
                    cityList.value = daoRepository.getOrderCityASC()

                }
                //nüfus
                3f -> {
                    cityList.value = daoRepository.getOrderPopulationDESC()
                }

                3.1f -> {
                    cityList.value = daoRepository.getOrderPopulationASC()
                }

                // favori şehirler
                4f -> {
                    cityList.value = daoRepository.getMyFav()
                }


            }

        }

    }

}