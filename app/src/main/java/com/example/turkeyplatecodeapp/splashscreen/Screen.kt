package com.example.turkeyplatecodeapp.splashscreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("city_list_screen")
    object Detail : Screen("city_detail_screen/{cityId}")
    object Game : Screen("plate_find_screen/{gameId}")
}