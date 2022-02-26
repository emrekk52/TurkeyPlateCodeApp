package com.example.turkeyplatecodeapp

import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptolistapp.viewmodel.CityListViewModel
import com.example.turkeyplatecodeapp.constants.Constants.oyunType
import com.example.turkeyplatecodeapp.splashscreen.AnimatedSplashScreen
import com.example.turkeyplatecodeapp.splashscreen.Screen
import com.example.turkeyplatecodeapp.ui.theme.TurkeyPlateCodeAppTheme
import com.example.turkeyplatecodeapp.view.CityDetailScreen
import com.example.turkeyplatecodeapp.view.CityListScreen
import com.example.turkeyplatecodeapp.view.PlateFindScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import isInternetAvailable
import org.jetbrains.annotations.Async
import java.lang.IllegalArgumentException


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)


        setContent {


            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
            }


            TurkeyPlateCodeAppTheme {
                val navController = rememberNavController()

                val context = this


                NavHost(navController, startDestination = Screen.Splash.route) {
                    composable(route = Screen.Home.route) {
                        CityListScreen(navController = navController)
                    }

                    composable(route = Screen.Splash.route) {
                        AnimatedSplashScreen(navController)
                    }

                    composable(route = Screen.Detail.route, arguments = listOf(
                        navArgument("cityId") {
                            type = NavType.StringType
                        }
                    )) {

                        val cityId = remember {
                            it.arguments?.getString("cityId")
                        }

                        CityDetailScreen(cityId = cityId ?: "", navController)
                    }




                    composable(route = Screen.Game.route, arguments = listOf(
                        navArgument("gameId") {
                            type = NavType.IntType
                        }
                    )) {


                        val gameId = remember {
                            it.arguments?.getInt("gameId", 0)
                        }

                        oyunType = gameId!!

                        if (oyunType != 3)
                            PlateFindScreen(navController = navController, context = context)
                        else
                            if (isInternetAvailable(context))
                                PlateFindScreen(navController = navController, context = context)
                            else
                                Toast.makeText(
                                    context,
                                    "İnternet bağlantısı bulunamadı!",
                                    Toast.LENGTH_SHORT
                                ).show()

                    }
                }

            }
        }
    }
}



















