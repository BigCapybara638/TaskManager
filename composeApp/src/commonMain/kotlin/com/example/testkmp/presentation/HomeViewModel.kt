package com.example.testkmp.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.usecases.GetAllTasksUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel(
    private val getAllTasksUseCase: GetAllTasksUseCase
) : ViewModel() {

    var _dataState = MutableStateFlow<DataState<List<Task>>>(DataState.Loading)
    val dataState: StateFlow<DataState<List<Task>>> = _dataState

    fun loadData() : List<Task> {
        return getAllTasksUseCase()
    }

}

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<T>(val data: T): DataState<T>()
    data class Error(val message: Exception) : DataState<Nothing>()
}