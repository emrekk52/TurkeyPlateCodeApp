package com.example.turkeyplatecodeapp.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptolistapp.viewmodel.CityListViewModel
import com.example.turkeyplatecodeapp.model.Ilceler
import com.example.turkeyplatecodeapp.ui.theme.*
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.RoundedCorner
import moe.tlaster.nestedscrollview.VerticalNestedScrollView
import moe.tlaster.nestedscrollview.rememberNestedScrollViewState

@Composable
fun CityDetailScreen(
    cityId: String,
    navController: NavController,
    viewModel: CityListViewModel = hiltViewModel()
) {

    val city by remember {
        mutableStateOf(viewModel.getFilterCity(idd = cityId))
    }
    val nestedScrollViewState = rememberNestedScrollViewState()
    VerticalNestedScrollView(
        modifier = Modifier.background(
            color = appBackgroundColor
        ), state = nestedScrollViewState, header = {

            Column {


                //TOP BAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .neu(
                                shadowElevation = 5.dp,
                                lightSource = LightSource.LEFT_BOTTOM,
                                shape = com.gandiva.neumorphic.shape.Pressed(RoundedCorner(50.dp)),
                                lightShadowColor = Color.White,
                                darkShadowColor = Color.LightGray
                            )
                            .background(color = appBackgroundColor, CircleShape)
                    ) {
                        Icon(
                            tint = selectedColor,
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "back button", modifier = Modifier.padding(5.dp)
                        )
                    }



                    Text(
                        text = city.il_adi,
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h3,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,

                        )

                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .neu(
                                shadowElevation = 5.dp,
                                lightSource = LightSource.LEFT_BOTTOM,
                                shape = com.gandiva.neumorphic.shape.Pressed(RoundedCorner(50.dp)),
                                lightShadowColor = Color.White,
                                darkShadowColor = Color.LightGray
                            )
                            .size(50.dp)
                            .background(color = appBackgroundColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = city.plaka_kodu,
                            color = selectedColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center

                        )
                    }


                }

                // kısa bilgi
                Column(modifier = Modifier.padding(5.dp)) {


                    Text(
                        text = city.kisa_bilgi, modifier = Modifier.padding(5.dp),
                        softWrap = true,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.h4
                    )


                }

            }


        }) {


        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {

            // nüfus , yüz ölçümü, nüfusta kapladığı alan
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .neu(
                        shadowElevation = 5.dp,
                        lightSource = LightSource.LEFT_BOTTOM,
                        shape = com.gandiva.neumorphic.shape.Pressed(RoundedCorner(50.dp)),
                        lightShadowColor = Color.White,
                        darkShadowColor = Color.LightGray
                    )
            ) {


                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                bottomStart = 20.dp
                            )
                        )
                        .weight(1f, fill = true)
                ) {
                    Text(
                        text = when (city.nufus.length) {
                            in 4..8 -> "${city.nufus}B"
                            else -> "${city.nufus}M"
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(10.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.White
                        )
                        .weight(1f, fill = true)
                ) {
                    Text(
                        text = "${city.yuzolcumu}km²",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(10.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 20.dp)
                        )
                        .weight(1f, fill = true)
                ) {
                    Text(
                        text = city.nufus_yuzdesi_genel,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(10.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }

            // diğer bilgiler
            var otherVisible by remember { mutableStateOf(false) }
            var ilceVisible by remember { mutableStateOf(true) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable { otherVisible = !otherVisible },
                elevation = 5.dp,
                backgroundColor = selectedColor
            ) {


                Column {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Diğer bilgiler", fontSize = 22.sp, color = Color.White)

                        Icon(
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = "diğer bilgiler",
                            tint = Color.White
                        )

                    }


                    AnimatedVisibility(
                        visible = otherVisible,
                        enter = fadeIn(
                            // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                            initialAlpha = 0.4f
                        ),
                        exit = fadeOut(
                            // Overwrites the default animation with tween
                            animationSpec = tween(durationMillis = 250)
                        )
                    ) {

                        Column {
                            OtherInfoRow(baslik = "Bölge", deger = city.bolge)
                            OtherInfoRow(baslik = "Kadın nüfus", deger = city.kadin_nufus)
                            OtherInfoRow(baslik = "Erkek nüfus", deger = city.erkek_nufus)
                            OtherInfoRow(
                                baslik = "Kadın nüfus oranı",
                                deger = city.kadin_nufus_yuzde
                            )
                            OtherInfoRow(
                                baslik = "Erkek nüfus oranı",
                                deger = city.erkek_nufus_yuzde
                            )

                        }


                    }
                }


            }


            //ilçeler
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .neu(
                        shadowElevation = 5.dp,
                        lightSource = LightSource.LEFT_BOTTOM,
                        shape = com.gandiva.neumorphic.shape.Flat(RoundedCorner(5.dp)),
                        lightShadowColor = Color.White,
                        darkShadowColor = Color.LightGray
                    )
                    .clickable { ilceVisible = !ilceVisible },

                backgroundColor = appBackgroundColor
            ) {


                Column {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "İlçeler(${city.ilceler.size})",
                            fontSize = 22.sp,
                            color = selectedColor
                        )

                        Icon(
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = "ilçeler",
                            tint = selectedColor
                        )

                    }


                    AnimatedVisibility(
                        visible = ilceVisible,
                        enter = fadeIn(
                            // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                            initialAlpha = 0.4f
                        ),
                        exit = fadeOut(
                            // Overwrites the default animation with tween
                            animationSpec = tween(durationMillis = 250)
                        )
                    ) {
                        IlceListView(ilceler = city.ilceler)

                    }
                }


            }


        }


    }


}


@Composable
fun IlceListView(ilceler: List<Ilceler>) {

    LazyColumn(contentPadding = PaddingValues(1.dp)) {

        items(ilceler) {
            OtherInfoRow(
                it,
                color = selectedColor,
                isArrowDropdownIcon = true
            )
        }

    }
}

@Composable
fun OtherInfoRow(baslik: String, deger: String, color: Color = Color.White) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = baslik, fontWeight = FontWeight.Black,
            color = color,

            )

        Text(
            text = deger,
            color = color,
        )

        val ilcelerVisible by remember {
            mutableStateOf(false)
        }


    }

}

@Composable
fun IlcelerListView(baslik: String, ilceBilgi: String, color: Color = Color.LightGray) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = baslik, fontWeight = FontWeight.Black,
            color = color,

            )



        Text(
            text = ilceBilgi,
            color = color,
        )
    }
}

@Composable
fun OtherInfoRow(
    ilceBilgi: Ilceler,
    color: Color = Color.White,
    isArrowDropdownIcon: Boolean = false
) {

    var ilcelerVisible by remember {
        mutableStateOf(false)
    }


    Column() {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    ilcelerVisible = !ilcelerVisible
                }
                .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Row(modifier = Modifier.align(Alignment.CenterVertically)) {

                Text(
                    text = ilceBilgi.ilce_adi, fontWeight = FontWeight.Black,
                    color = color,

                    )


                if (isArrowDropdownIcon)
                    Icon(
                        tint = color,
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = "ilce bilgileri"
                    )
            }



            Text(
                text = ilceBilgi.nufus,
                color = color,
            )


        }

        AnimatedVisibility(
            visible = ilcelerVisible,
            enter = fadeIn(
                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                initialAlpha = 0.4f
            ),
            exit = fadeOut(
                // Overwrites the default animation with tween
                animationSpec = tween(durationMillis = 250)
            )
        ) {
            val textColor = purple

            Column(modifier = Modifier.padding(6.dp)) {
                IlcelerListView(
                    "Erkek nüfus",
                    ilceBilgi = if (ilceBilgi.erkek_nufus.isEmpty()) " - " else ilceBilgi.erkek_nufus,
                    color = textColor
                )
                IlcelerListView(
                    "Kadın nüfus",
                    ilceBilgi = if (ilceBilgi.kadin_nufus.isEmpty()) " - " else ilceBilgi.kadin_nufus,
                    color = textColor
                )
                IlcelerListView(
                    "Yüz ölçümü",
                    ilceBilgi =
                    if (ilceBilgi.yuzolcumu.isEmpty()) " - " else "${ilceBilgi.yuzolcumu}km²",
                    color = textColor
                    )
                }

            }

        }

    }


