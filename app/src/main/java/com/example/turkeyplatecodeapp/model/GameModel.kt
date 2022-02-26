package com.example.turkeyplatecodeapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class GameModel(
    val id: Int,
    val gameName: String,
    val gameIcon: Int,
    val tintColor: Color = Color.White,
    val gameDescript: String

)
