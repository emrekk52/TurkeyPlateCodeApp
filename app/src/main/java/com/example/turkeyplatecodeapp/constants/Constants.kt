package com.example.turkeyplatecodeapp.constants

import android.view.View
import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import com.example.turkeyplatecodeapp.R
import com.example.turkeyplatecodeapp.model.GameModel
import com.example.turkeyplatecodeapp.model.OrderModel


object Constants {

    const val BASE_URL = "https://raw.githubusercontent.com/"


    var oyunType=0

    val gameList = listOf(
        GameModel(0, "Plaka bul", R.drawable.plate, gameDescript = "Rastgele gelen 10 ilin plakasını bulmaya çalışın"),
        GameModel(1, "Şehir bul", R.drawable.plate_reverse, gameDescript = "Rastgele gelen 10 plakanın hangi ile ait olduğunu bulmaya çalışan"),
        GameModel(2, "İlçe bul", R.drawable.city, gameDescript = "Hangi ilçenin hangi ile ait olduğunu bulmaay çalışın"),
        GameModel(3, "Haritadan il bul", R.drawable.turkey_map, gameDescript = "Haritada verilen ilin nereye ait olduğunu bulun"),
    )

    val orderCityList = listOf(
        OrderModel(0.1f, "Plaka kodu(artan)"),
        OrderModel(0f, "Plaka kodu(azalan)"),
        OrderModel(1.1f, "Telefon kodu(artan)"),
        OrderModel(1f, "Telefon kodu(azalan)"),
        OrderModel(2.1f, "Şehir(artan)"),
        OrderModel(2f, "Şehir(azalan)"),
        OrderModel(3.1f, "Nüfus(artan)"),
        OrderModel(3f, "Nüfus(azalan)"),
        OrderModel(4f, "Favoriler"),

        )

}

