package com.example.turkeyplatecodeapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

class QuestionModel {

    var plate: String = ""
    var answer: String = ""
    var cityMapUrl: String = ""
    var id: Int = 0
    lateinit var answerList: List<String>


}
