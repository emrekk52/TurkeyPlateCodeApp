package com.example.turkeyplatecodeapp.view


import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cryptolistapp.viewmodel.CityListViewModel
import com.example.turkeyplatecodeapp.R
import com.example.turkeyplatecodeapp.constants.Constants.gameList
import com.example.turkeyplatecodeapp.constants.Constants.orderCityList
import com.example.turkeyplatecodeapp.model.Data
import com.example.turkeyplatecodeapp.model.GameModel
import com.example.turkeyplatecodeapp.ui.theme.*
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.RoundedCorner
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.Pot
import me.nikhilchaudhari.library.shapes.Pressed
import me.nikhilchaudhari.library.shapes.Punched
import moe.tlaster.nestedscrollview.VerticalNestedScrollView
import moe.tlaster.nestedscrollview.rememberNestedScrollViewState
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CityListScreen(
    navController: NavController,
    viewModel: CityListViewModel = hiltViewModel()
) {

    val nestedScrollViewState = rememberNestedScrollViewState()
    var mQuery by remember {
        mutableStateOf("")
    }

    VerticalNestedScrollView(modifier = Modifier.background(appBackgroundColor),
        state = nestedScrollViewState, header = {

            Column {

                Row(
                    modifier = Modifier.padding(5.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Şehirler",
                        modifier = Modifier
                            .padding(15.dp)
                            .weight(1f),
                        textAlign = TextAlign.Start,
                        fontSize = 40.sp,
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.ExtraBold,
                        color = textEndButtonTextColor,
                    )


                }


                GameList(navController)


            }

        }, content = {

            Column(modifier = Modifier.fillMaxSize()) {

                //search bar
                SearchBar(
                    modifier = Modifier.padding(16.dp)
                ) {
                    viewModel.searchCityList(it)
                    mQuery = it
                }

                //dropdown order menu
                DropDownMenu()

                //city list
                CityList(navController = navController, _query = mQuery)

            }

        })


}


@Composable
fun DropDownMenu(viewModel: CityListViewModel = hiltViewModel()) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOrder by remember { mutableStateOf(orderCityList[0].order) }

    Box(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier
            //no ripple effect
            .clickable(indication = null, interactionSource = remember {
                MutableInteractionSource()
            }) {
                expanded = !expanded
            }
            .padding(end = 10.dp, bottom = 10.dp)
            .align(Alignment.TopEnd)
            .background(color = selectedColor, RoundedCornerShape(8.dp))
        ) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }) {
                orderCityList.forEach { order ->
                    DropdownMenuItem(onClick = {
                        selectedOrder = order.order
                        expanded = false
                        viewModel.orderList(order.id)
                    }) {

                        val isSelected = selectedOrder == order.order
                        val mStyle = if (isSelected) {
                            MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold,
                                color = selectedColor
                            )
                        } else {
                            MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Normal,
                                color = notSelectedColor
                            )
                        }

                        Text(order.order, Modifier.padding(12.dp), style = mStyle)
                    }

                }


            }

            Row(modifier = Modifier.padding(5.dp)) {
                Text(text = selectedOrder, color = Color.White) // City name label
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    tint = Color.White,
                    contentDescription = "drop down menu"
                )
            }

        }
    }

}

@Composable
fun GameList(navController: NavController) {


    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(shape = RoundedCornerShape(7.dp), color = Color.White)
    ) {

        Text(
            text = "Oyunlar",
            modifier = Modifier
                .padding(5.dp),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold
        )

    }

    LazyRow {
        items(gameList) {
            GameTab(it, navController)
        }
    }

}

@Composable
fun GameTab(gameModel: GameModel, navController: NavController) {

    Row(modifier = Modifier.padding(7.dp)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    navController.navigate("plate_find_screen/${gameModel.id}")
                }
                .size(width = 200.dp, height = 140.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            lightWhite,
                            lightGameShapePurple
                        )
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

        ) {

            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Text(
                    modifier = Modifier
                        .padding(2.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    text = gameModel.gameName,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis
                )

                Image(
                    modifier = Modifier
                        .padding(2.dp)
                        .height(70.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    contentDescription = gameModel.gameName,
                    painter = painterResource(gameModel.gameIcon),
                )


            }

            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                text = gameModel.gameDescript,
                fontSize = 16.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis
            )

        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {
    }
) {

    var text by remember {
        mutableStateOf("")
    }



    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .background(color = Color.Transparent)
    ) {


        TextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            label = {
                Text("Ara", color = Color.LightGray)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
            ),
            leadingIcon = {
                Icon(Icons.Filled.Search, "arama ikonu")
            },
            trailingIcon = {
                if (text.isNotEmpty())
                    Icon(Icons.Filled.Clear, "silme ikonu",
                        modifier = Modifier
                            .clickable {
                                text = ""
                                onSearch(text)
                            }
                    )

            },
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(text)
            }),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = appBackgroundColor,
                cursorColor = selectedColor,
                leadingIconColor = selectedColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .neumorphic()
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
        )
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityListView(citys: List<Data>, navController: NavController) {

    LazyVerticalGrid(cells = GridCells.Fixed(2)) {

        items(citys) {
            CityRow(navController = navController, it)
        }

    }

}


@Composable
fun CityRow(navController: NavController, city: Data) {

    Column(Modifier.padding(10.dp)) {


        Card(
            modifier = Modifier
                .neumorphic(neuShape = Punched.Rounded(8.dp))
                .clickable { navController.navigate("city_detail_screen/${city.id}") }
            ,
            shape = RoundedCornerShape(8.dp),
            backgroundColor = appBackgroundColor

        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {


                // telefon kodu
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)

                ) {

                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .border(2.dp, color = selectedColor, RoundedCornerShape(5.dp))
                            .align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = "+${city.alan_kodu}",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = textEndButtonTextColor,
                            modifier = Modifier.padding(5.dp)


                        )
                    }


                }



                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    // plaka
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(5.dp),
                        text = city.plaka_kodu,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.h3,
                        fontSize = 80.sp,
                        textAlign = TextAlign.Center,
                        color = textEndButtonTextColor,
                    )
                }

                // il adi
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = city.il_adi,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h4,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = textEndButtonTextColor
                )


                // nüfus
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = when (city.nufus.trim().length) {
                        in 4..7 -> "${city.nufus} bin"
                        in 7..10 -> "${city.nufus} milyon"
                        else -> "${city.nufus}Mr"
                    },
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.h5,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = subTitleColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )

                FavoriteButton(id = city.id, isFavorite = city.isFavorite)

            }

        }

    }


}


@Composable
fun CityList(
    navController: NavController,
    viewModel: CityListViewModel = hiltViewModel(),
    _query: String,
) {


    val cityList by remember { viewModel.cityList }

    val error by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }



    CityListView(citys = cityList, navController = navController)



    if (cityList.isEmpty() && _query.isNotEmpty()) {

        Box(modifier = Modifier.fillMaxSize())
        {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            ) {


                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "sonuç bulunamadı",
                    tint = purple,
                    modifier = Modifier.size(40.dp)
                )

                Text(
                    text = "$_query ile eşleşen sonuç bulunamadı",
                    textAlign = TextAlign.Center, color = subTitleColor,
                    modifier = Modifier.align(Alignment.CenterVertically)

                )


            }
        }


    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {

        if (isLoading)
            CircularProgressIndicator(color = Color.Cyan)

        if (error.isNotEmpty())

            RetryView(error = error) {
                viewModel.loadCityList()
            }

    }

}


@Composable
fun RetryView(error: String, onRetry: () -> Unit) {


    Column {

        Text(
            text = error, color = Color.Red,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = onRetry, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Yenile")
        }

    }


}


@Composable
fun FavoriteButton(
    color: Color = selectedColor,
    id: Int,
    isFavorite: Boolean,
    viewModel: CityListViewModel = hiltViewModel()
) {

    var isFav by remember { mutableStateOf(isFavorite) }

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFav = !isFav
            viewModel.updateFavorite(id, isFav)
        }
    ) {
        Icon(
            tint = color,

            imageVector = if (isFav) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = "favori ikon"
        )
    }

}


