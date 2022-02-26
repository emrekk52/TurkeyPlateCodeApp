package com.example.turkeyplatecodeapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cryptolistapp.viewmodel.PlateFindViewModel
import com.example.turkeyplatecodeapp.ui.theme.*
import com.example.turkeyplatecodeapp.view.SomeIcons
import kotlinx.coroutines.launch


@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    plateFindViewModel: PlateFindViewModel,
    navController: NavController
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier
                .background(Color.White)
        ) {

            //.......................................................................
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null, // decorative
                tint = gameShapePurple,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Oyun bitti!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h2,
                    fontFamily = futura,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                val gameEndText = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = gameShapePurple)) {
                        append("Yapılan doğru sayısı : ")
                    }

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = selectedColor
                        )
                    ) {
                        append("${plateFindViewModel.trueAnswerCount}")
                    }
                    withStyle(style = SpanStyle(color = gameShapePurple)) {
                        append(", yapılan yanlış sayısı ise : ")
                    }

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = selectedColor
                        )
                    ) {
                        append("${plateFindViewModel.falseAnswerCount}")
                    }

                }

                val gameAllTrueText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = selectedColor
                        )
                    ) {
                        append("Tebrikler tüm soruları doğru cevapladınız!")
                    }
                }

                Text(
                    text = if (plateFindViewModel.trueAnswerCount == plateFindViewModel.questionSize.value)
                        gameAllTrueText
                    else gameEndText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body2
                )
            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(lightGameShapePurple),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = {
                    navController.navigateUp()
                }) {

                    Text(
                        style = MaterialTheme.typography.body2,
                        text = "Oyunu bitir",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = {
                    plateFindViewModel.retryLoadGame()
                }) {
                    Text(
                        "Tekrar oyna",
                        fontWeight = FontWeight.ExtraBold,
                        color = gameShapePurple,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}


@Composable
fun SubmitQuestionDialogUI(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    plateFindViewModel: PlateFindViewModel,
    navController: NavController,
    context: Context
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {

            //.......................................................................
            Icon(
                painter = painterResource(id = SomeIcons.Quiz),
                contentDescription = null, // decorative
                tint = gameShapePurple,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    textAlign = TextAlign.Center,
                    text = "Cevabın yanlış olduğunu düşünüyorsanız bu soruyu bildirebilirsiniz"
                )

                val scope = rememberCoroutineScope()

                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {

                    Button(
                        onClick = {
                            scope.launch {

                                Toast.makeText(
                                    context,
                                    "Soru incelendikten sonra değişikler yapılacaktır",
                                    Toast.LENGTH_SHORT
                                ).show()

                                openDialogCustom.value = false
                            }

                        },
                        modifier = Modifier
                            .weight(0.6f)

                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = selectedColor)

                    ) {
                        Text(text = "Bildir")
                    }

                    Button(
                        onClick = { openDialogCustom.value = false },
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = notSelectedColor)

                    ) {
                        Text(text = "Kapat", color = Color.White)
                    }

                }
            }

        }
    }
}


@Preview
@Composable
fun preview() {

    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {

            //.......................................................................
            Icon(
                painter = painterResource(id = SomeIcons.Quiz),
                contentDescription = null, // decorative
                tint = gameShapePurple,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    textAlign = TextAlign.Center,
                    text = "Cevabın yanlış olduğunu düşünüyorsanız bu soruyu bildirebilirsiniz"
                )


                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(0.7f)

                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = selectedColor)

                    ) {
                        Text(text = "Bildir")
                    }

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(0.3f)
                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = notSelectedColor)

                    ) {
                        Text(text = "Kapat", color = Color.White)
                    }

                }
            }

        }
    }
}


