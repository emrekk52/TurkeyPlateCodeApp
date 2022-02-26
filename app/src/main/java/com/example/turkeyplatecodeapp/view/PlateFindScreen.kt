package com.example.turkeyplatecodeapp.view


import Dimensions
import MediaQuery
import android.app.Dialog
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.cryptolistapp.viewmodel.PlateFindViewModel
import com.example.turkeyplatecodeapp.CorrectAnimation
import com.example.turkeyplatecodeapp.CustomDialogUI
import com.example.turkeyplatecodeapp.R
import com.example.turkeyplatecodeapp.SubmitQuestionDialogUI
import com.example.turkeyplatecodeapp.constants.Constants.oyunType
import com.example.turkeyplatecodeapp.extensions.ZoomableComposable
import com.example.turkeyplatecodeapp.extensions.ZoomableImage
import com.example.turkeyplatecodeapp.ui.theme.*
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.RoundedCorner

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lessThan
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.Punched
import moe.tlaster.nestedscrollview.VerticalNestedScrollView
import moe.tlaster.nestedscrollview.rememberNestedScrollViewState


@Composable
fun PlateFindScreen(
    navController: NavController,
    plateFindViewModel: PlateFindViewModel = hiltViewModel(),
    context: Context
) {

    val isTrue by plateFindViewModel.isTrue.observeAsState()

    val gameOver by plateFindViewModel.gameOver.observeAsState()

    if (isTrue!!)
        CorrectAnimation()


    BackCard()


    TopBar(
        16.dp, 36.dp, { navController.navigateUp() },
        plateFindViewModel, context, navController
    )


    var closeDialog by remember {
        plateFindViewModel.closeDialog
    }


    if (!closeDialog && gameOver!!)
        CustomDialog(
            openDialogCustom = mutableStateOf(false),
            plateFindViewModel,
            navController
        )


}


@Composable
fun CustomDialog(
    openDialogCustom: MutableState<Boolean>,
    plateFindViewModel: PlateFindViewModel,
    navController: NavController
) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUI(
            openDialogCustom = openDialogCustom,
            plateFindViewModel = plateFindViewModel,
            navController = navController
        )
    }
}

@Composable
fun BackCard(height: Int = 6) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height((LocalContext.current.resources.displayMetrics.widthPixels / height).dp),
        backgroundColor = gameShapePurple,
        shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),


        ) {

        Column(modifier = Modifier.fillMaxSize()) {


            Row {


                Box(
                    modifier = Modifier
                        .offset(x = -55.dp, y = -10.dp)
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(
                            alpha = 0.5f,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFA764DB), Color(0xFFA764DB)
                                )
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .offset(y = -45.dp)
                        .padding(start = 20.dp)
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(
                            alpha = 0.5f,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFA764DB), Color(0xFFA764DB)
                                )
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .padding(start = 60.dp, top = 20.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(
                            alpha = 0.5f,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFA764DB), Color(0xFFA764DB)
                                )
                            )
                        )
                )


            }


        }


    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun CardInside(plateFindViewModel: PlateFindViewModel = hiltViewModel()) {
    Column {

        var questionSize by remember {
            plateFindViewModel.questionSize
        }
        var questionIndex by remember {
            plateFindViewModel.questionIndex
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Row(modifier = Modifier.weight(1f)) {


                // geçilen soru sayısı
                Text(
                    text = "${questionSize - questionIndex - 1}",
                    fontSize = 18.sp,
                    color = passedQuestionColor,
                    fontWeight = FontWeight.Bold
                )

                LinearRoundedProgressIndicator(
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                        .clip(RoundedCornerShape(30.dp)),
                    backgroundColor = Color.Transparent,
                    color = passedQuestionColor,
                    progress = 1f - (questionIndex * 0.1f),

                    )
            }

            val kalanSoru = buildAnnotatedString {
                withStyle(style = SpanStyle(color = gameShapePurple)) {
                    append("Soru ")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = selectedColor
                    )
                ) {
                    append("${questionSize - questionIndex}")
                }
                withStyle(style = SpanStyle(color = gameShapePurple)) {
                    append("/${questionSize}")
                }

            }



            Text(kalanSoru, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)


            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {


                LinearRoundedProgressIndicator(
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)
                        .clip(RoundedCornerShape(30.dp))
                        .graphicsLayer {
                            rotationY = 180f
                        }
                        .weight(9f),
                    backgroundColor = Color.Transparent,
                    color = remainQuestionColor,
                    progress = questionIndex * 0.1f
                )

                Text(
                    text = "${questionIndex}",
                    fontSize = 18.sp,
                    color = remainQuestionColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )


            }

        }

        val questionList by remember {
            plateFindViewModel.questionList
        }


        val soru = buildAnnotatedString {

            if (oyunType != 3) {
                withStyle(
                    style = SpanStyle(
                        color = notSelectedColor, fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                ) {
                    append("${questionList[questionIndex].plate} ")
                }
            }

            withStyle(
                style = SpanStyle(
                    color = questionColor,
                    fontSize = 20.sp,
                )
            ) {


                when (oyunType) {

                    0 -> append("Hangi ilimizin plakasıdır?")
                    1 -> append("ilimizin plakası hangisidir?")
                    2 -> append("iline ait ilçe hangisidir?")
                    else -> append("Haritada ki işaretli yer hangi ilimizdir?")

                }


            }


        }


        if (oyunType == 3) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .border(
                        width = 2.dp,
                        shape = RoundedCornerShape(5.dp),
                        color = lightGameShapePurple
                    )
                    .heightIn(max = 180.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ZoomableImage(url = plateFindViewModel.nowQuestion.value.cityMapUrl)
//     ZoomableComposable(url = plateFindViewModel.nowQuestion.value.cityMapUrl)
            }


        }


        Text(
            soru, modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 30.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )


    }
}


@Composable
fun AnswerBlock(
    close: Boolean,
    plateFindViewModel: PlateFindViewModel,
    otherAnswer: String,
    answer: String,
    context: Context
) {


    var selected by remember {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .padding(10.dp)
            .neu(
                shadowElevation = 5.dp,
                lightSource = LightSource.RIGHT_TOP,
                shape = com.gandiva.neumorphic.shape.Pressed(RoundedCorner(15.dp)),
                lightShadowColor = Color.White,
                darkShadowColor = when (selected) {
                    0 -> Color.LightGray
                    1 -> passedQuestionColor
                    else -> remainQuestionColor
                }
            )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .clickable {

                    if (close)
                        return@clickable

                    if (plateFindViewModel.isAnswerTrue(otherAnswer, context)) {
                        selected = 1
                    } else
                        selected = 2


                    plateFindViewModel.passQuestion(context)


                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                text = otherAnswer, style = TextStyle(
                    color = answerColor, fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            )


            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
                    .border(
                        2.dp,
                        color = lightWhite,
                        CircleShape
                    )
                    .background(
                        color = when (selected) {
                            0 -> lightWhite
                            1 -> passedQuestionColor
                            else -> remainQuestionColor
                        },
                        shape = CircleShape
                    )
            ) {

                if (selected != 0)
                    Icon(
                        imageVector = when (selected) {
                            1 -> Icons.Rounded.Done
                            else -> Icons.Rounded.Close
                        },
                        contentDescription = "correct answer",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(15.dp)
                    )
            }


        }
    }

    scope.launch {
        delay(1500)
        selected = 0
    }


}


@Composable
fun LinearRoundedProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity)
) {
    val linearIndicatorHeight = ProgressIndicatorDefaults.StrokeWidth
    val linearIndicatorWidth = 240.dp
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(linearIndicatorWidth, linearIndicatorHeight)
            .focusable()
    ) {
        val strokeWidth = size.height
        drawRoundedLinearIndicatorBackground(backgroundColor, strokeWidth)
        drawRoundedLinearIndicator(0f, progress, color, strokeWidth)
    }
}

private fun DrawScope.drawRoundedLinearIndicatorBackground(
    color: Color,
    strokeWidth: Float
) = drawRoundedLinearIndicator(0f, 1f, color, strokeWidth)

private fun DrawScope.drawRoundedLinearIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float
) {
    val width = size.width
    val height = size.height
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width

    // Progress line
    drawLine(
        color,
        Offset(barStart, yOffset),
        Offset(barEnd, yOffset),
        strokeWidth,
        StrokeCap.Round
    )
}


object SomeIcons {
    val Quiz = R.drawable.outline_quiz_24

}

@Composable
fun TopBar(
    padding: Dp, iconSize: Dp, onClick: () -> Unit,
    plateFindViewModel: PlateFindViewModel, context: Context,
    navController: NavController
) {

    Column {


        //TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding), horizontalArrangement = Arrangement.SpaceBetween
        ) {


            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .background(color = Color.White, CircleShape)
                    .size(iconSize)
            ) {
                Icon(
                    tint = selectedColor,
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "back button", modifier = Modifier.padding(5.dp)
                )
            }


            var submitQuestionDialog = remember {
                mutableStateOf(false)
            }




            if (submitQuestionDialog.value)
                Dialog(onDismissRequest = { submitQuestionDialog.value = false }) {
                    SubmitQuestionDialogUI(
                        openDialogCustom = submitQuestionDialog,
                        plateFindViewModel = plateFindViewModel,
                        navController = navController,
                        context = context
                    )
                }



            IconButton(
                onClick = { submitQuestionDialog.value = true }, modifier = Modifier.size(iconSize)
            ) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(id = SomeIcons.Quiz),
                    contentDescription = "setting button", modifier = Modifier.padding(5.dp)
                )
            }


        }

        QuestionScreen(context = LocalContext.current)


    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionScreen(plateFindViewModel: PlateFindViewModel = hiltViewModel(), context: Context) {
    Column(
        modifier = Modifier
            .padding(
                top = 50.dp,
                bottom = 10.dp,
                end = 10.dp,
                start = 10.dp
            )
            .fillMaxWidth()
    ) {


        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.neumorphic(
                neuShape = Punched.Rounded(8.dp),
                lightShadowColor = Color.Transparent,
                darkShadowColor = Color(0xFFD3ADF0),
                elevation = 2.dp,
                strokeWidth = 2.dp
            )
        ) {
            CardInside(plateFindViewModel)
        }


        Column(modifier = Modifier.padding(vertical = 30.dp, horizontal = 10.dp)) {


            var nowQuestion by remember {
                plateFindViewModel.nowQuestion
            }

            val close by plateFindViewModel.close.observeAsState()


            if (oyunType == 3)
                LazyVerticalGrid(cells = GridCells.Fixed(2)) {

                    items(nowQuestion.answerList) { question ->
                        AnswerBlock(
                            close!!,
                            plateFindViewModel,
                            question,
                            nowQuestion.answer,
                            context = context
                        )
                    }

                }
            else

                LazyColumn(contentPadding = PaddingValues(2.dp)) {
                    items(nowQuestion.answerList) { question ->
                        AnswerBlock(
                            close!!,
                            plateFindViewModel,
                            question,
                            nowQuestion.answer,
                            context = context
                        )
                    }
                }


        }


    }
}

