package com.example.newyear.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newyear.data.CountdownState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.seconds

class CountdownViewModel : ViewModel() {

    private val _targetTime = MutableStateFlow(LocalDateTime.now().getNextNewYear())
    val targetTime: StateFlow<LocalDateTime> = _targetTime.asStateFlow()

    private val _uiState = MutableStateFlow(CountdownState())
    val uiState: StateFlow<CountdownState> = _uiState.asStateFlow()

    private val _isInCelebrationMode = MutableStateFlow(false)
    val isInCelebrationMode: StateFlow<Boolean> = _isInCelebrationMode.asStateFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (isActive) {
                if (!_isInCelebrationMode.value) {
                    val newState = calculateRemaining(_targetTime.value)
                    _uiState.value = newState
                    
                    if (newState.isNewYear) {
                        _isInCelebrationMode.value = true
                    }
                }
                delay(1.seconds)
            }
        }
    }

    fun resetToNextNewYear() {
        _targetTime.value = LocalDateTime.now().getNextNewYear()
        _isInCelebrationMode.value = false
    }

    fun triggerCelebrationNow() {
        // Set target time to now (or just slightly in the past) to trigger isNewYear
        _targetTime.value = LocalDateTime.now().minusSeconds(1)
        // calculateRemaining will return isNewYear = true
        val newState = calculateRemaining(_targetTime.value)
        _uiState.value = newState
        _isInCelebrationMode.value = true
    }
}
