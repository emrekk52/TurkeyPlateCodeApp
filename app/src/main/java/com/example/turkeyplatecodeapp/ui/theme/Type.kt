package com.example.turkeyplatecodeapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.turkeyplatecodeapp.R


val BRCandor = FontFamily(
    Font(R.font.brcandor_semibold, FontWeight.ExtraBold),
    Font(R.font.brcandor_regular)
)

val futura = FontFamily(
    Font(R.font.futura_bold, FontWeight.Bold),
    Font(R.font.futura_light_font, FontWeight.Normal)
)


// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h3 = TextStyle(
        fontFamily = BRCandor,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */, body2 = TextStyle(

        fontFamily = futura,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
)
