package com.example.turkeyplatecodeapp

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext


fun CorrectPlay(context: Context) {
    val mp: MediaPlayer = MediaPlayer.create(context, R.raw.correct)
    mp.start()
}


fun WrongPlay(context: Context) {
    val mp: MediaPlayer = MediaPlayer.create(context, R.raw.incorrect)
    mp.start()
}


fun GameCompleted(context: Context) {
    val mp: MediaPlayer = MediaPlayer.create(context, R.raw.game_completed)
    mp.start()
}