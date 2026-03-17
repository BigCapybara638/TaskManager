package com.example.testkmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkmp.data.supabase
import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.usecases.AddCategoryUseCase
import com.example.testkmp.domain.usecases.GetAllCategoriesUseCase
import com.example.testkmp.domain.usecases.GetAllTasksUseCase
import com.example.testkmp.domain.usecases.GetTasksInCategoryUseCase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getTasksInCategoryUseCase: GetTasksInCategoryUseCase,

    ) : ViewModel() {

    var _dataState = MutableStateFlow<DataState<List<Categories>>>(DataState.Loading)
    val dataState: StateFlow<DataState<List<Categories>>> = _dataState

    fun addCategory(category: Categories)  {
        viewModelScope.launch {
            addCategoryUseCase(category)
            loadCatsData(supabase.auth.currentSessionOrNull()?.user?.id)
        }
    }

    fun loadCatsData(userId: String?) {

        viewModelScope.launch {
            _dataState.value = DataState.Loading
            try {
                val result = DataState.Success(getAllCategoriesUseCase.invoke(userId!!))
                _dataState.value = result
            } catch (e: Exception) {
                _dataState.value = DataState.Error(e)
                println(e.message.toString())
            }
        }
    }

    fun loadTasksInCategory(category: Categories) : List<Task> {
        return getTasksInCategoryUseCase(category)
    }


}

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<T>(val data: T): DataState<T>()
    data class Error(val message: Exception) : DataState<Nothing>()
}