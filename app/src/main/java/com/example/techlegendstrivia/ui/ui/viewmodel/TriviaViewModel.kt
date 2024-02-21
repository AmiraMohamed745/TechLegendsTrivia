package com.example.techlegendstrivia.ui.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techlegendstrivia.data.QuestionsRepository
import com.example.techlegendstrivia.model.Level
import com.example.techlegendstrivia.ui.ui.state.TriviaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log


@HiltViewModel
class TriviaViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TriviaUiState())
    val uiState: StateFlow<TriviaUiState> = _uiState.asStateFlow()

    init {
        loadQuestion()
    }

    fun skipQuestion() {
        val skipTimesAvailable = _uiState.value.skipTimes
        Log.d("TriviaViewModel", "Before skipping, skipTimesAvailable: $skipTimesAvailable")
        if (skipTimesAvailable > 0) {
            loadQuestion()
            _uiState.update {
                it.copy(skipTimes = it.skipTimes - 1)
            }
            Log.d("TriviaViewModel", "After skipping: ${_uiState.value}")
        } else {
            Log.d("TriviaViewModel", "No skips left")
        }
    }

    fun checkAnswer(answer: Int) {
        viewModelScope.launch {
            evaluateAnswer(answer)
            _uiState.update { it.copy(isAnswerSelected = true) }
            delay(1300)
            _uiState.update { it.copy(isQuestionVisible = false) }
            delay(1000)
            loadQuestion()
            _uiState.update { it.copy(isAnswerSelected = false, isQuestionVisible = true) }
        }
    }

    private fun evaluateAnswer(answer: Int) {
        val currentQuestion = _uiState.value.currentQuestion
        val correctAnswer = currentQuestion?.correctAnswer
        val isCorrect = answer == currentQuestion?.correctAnswer
        val highlightedCorrectAnswer =
            if (isCorrect) null else correctAnswer
        val correctAnswerImage = currentQuestion?.let {
            questionsRepository.getImageForAnswer(it.correctAnswer)
        } ?: _uiState.value.correctAnswerImage

        _uiState.update { triviaUiState ->
            triviaUiState.copy(
                isAnswerCorrect = isCorrect,
                selectedAnswer = answer,
                highlightedCorrectAnswer = highlightedCorrectAnswer,
                correctAnswerImage = correctAnswerImage
            )
        }
    }

    fun loadQuestion() {
        val currentLevel = _uiState.value.currentLevel
        val question = questionsRepository.getQuestionForLevel(currentLevel)
        _uiState.update { triviaUiState ->
            triviaUiState.copy(currentQuestion = question)
        }
    }

    fun setLevel(level: Level) {
        Log.d(
            "TriviaViewModel", "Setting level to: ${level.name}, Skips allowed: ${level.skips}"
        )
        _uiState.update { triviaUiState ->
            triviaUiState.copy(
                currentLevel = level,
                skipTimes = level.skips
            )
        }
        Log.d("TriviaViewModel", "New state: ${_uiState.value}")
    }
}


