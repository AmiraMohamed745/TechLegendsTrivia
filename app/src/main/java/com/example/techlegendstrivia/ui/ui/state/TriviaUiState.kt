package com.example.techlegendstrivia.ui.ui.state

import com.example.techlegendstrivia.R
import com.example.techlegendstrivia.model.Level
import com.example.techlegendstrivia.model.Question

data class TriviaUiState(
    val currentLevel: Level = Level.EASY,
    val currentQuestion: Question? = null,
    val selectedAnswer: Int? = null,
    val highlightedCorrectAnswer: Int? = null,
    val isAnswerSelected: Boolean = false,
    val isAnswerCorrect: Boolean = false,
    val correctAnswerImage: Int = R.drawable.image_placeholder,
    val isQuestionVisible: Boolean = true,
    val skipTimes: Int = currentLevel.skips,
    val score: Float = 0.0f
)

