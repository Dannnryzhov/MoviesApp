package com.example.moviesapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.presentation.sources.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _errorEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val errorEvent = _errorEvent.asSharedFlow()

    private val _eventFlow = MutableSharedFlow<Event>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("BaseViewModel", "Ошибка: ${exception.localizedMessage}", exception)

        viewModelScope.launch {
            _errorEvent.emit("Сообщение: ${exception.localizedMessage}")
        }
    }

    protected fun sendEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}
