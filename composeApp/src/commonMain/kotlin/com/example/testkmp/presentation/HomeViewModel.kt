package com.example.testkmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkmp.data.models.Message
import com.example.testkmp.data.supabase
import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.usecases.add.AddCategoryUseCase
import com.example.testkmp.domain.usecases.GetAllCategoriesUseCase
import com.example.testkmp.domain.usecases.GetAllTasksUseCase
import com.example.testkmp.domain.usecases.GetMessageFromGigachatUseCase
import com.example.testkmp.domain.usecases.GetTasksInCategoryUseCase
import com.example.testkmp.domain.usecases.update.UpdateCompletedStateUseCase
import com.example.testkmp.domain.usecases.add.AddTaskUseCase
import com.example.testkmp.domain.usecases.delete.DeleteCategoryUseCase
import com.example.testkmp.domain.usecases.delete.DeleteTaskUseCase
import com.example.testkmp.domain.usecases.update.UpdateTaskUseCase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getTasksInCategoryUseCase: GetTasksInCategoryUseCase,
    private val getMessageFromGigachatUseCase: GetMessageFromGigachatUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val updateCompletedStateUseCase: UpdateCompletedStateUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
    ) : ViewModel() {

    private var updateJob: Job? = null

    var _dataState = MutableStateFlow<DataState<List<Categories>>>(DataState.Loading)
    val dataState: StateFlow<DataState<List<Categories>>> = _dataState

    var _tasksState = MutableStateFlow<DataState<List<Task>>>(DataState.Loading)
    val tasksState: StateFlow<DataState<List<Task>>> = _tasksState.asStateFlow()

    var _gigachatState = MutableStateFlow(Result.success("Загрузка..."))
    val gigachatState: StateFlow<Result<String>> = _gigachatState

    var _floatingButtonState = MutableStateFlow(false)
    val floatingButtonState: StateFlow<Boolean> = _floatingButtonState

    init {
        getMessage()
    }

    fun addCategory(category: Categories)  {
        viewModelScope.launch {
            addCategoryUseCase(category)
            reloadCatsData(supabase.auth.currentSessionOrNull()?.user?.id)
        }
    }

    fun addTask(task: Task)  {
        viewModelScope.launch {
            addTaskUseCase(task)
            loadTasksData(supabase.auth.currentSessionOrNull()?.user?.id)
        }
    }

    fun updateIsCompletedState(task: Task) {

        // обновление задачи заранее для мнгновенного отклика
        val newList = (_tasksState.value as? DataState.Success)?.data?.map {
            if (it.id == task.id) it.copy(completed = !it.completed) else it
        } ?: return
        _tasksState.value = DataState.Success(newList)

        // отмена ранее начатых корутин
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            try {
                // запрос в бд + реальное обновление списка
                updateCompletedStateUseCase(task)
                loadTasksData(supabase.auth.currentSessionOrNull()?.user?.id)
            } catch (e: Exception) {
                println(e)
                // обновление списка если была ошибка
                loadTasksData(supabase.auth.currentSessionOrNull()?.user?.id)
            }
        }
        updateFloatingButtonStateTrue()
    }

    fun getMessage() {
        viewModelScope.launch {
            try {
                //_gigachatState.value = getMessageFromGigachatUseCase()

            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun updateFloatingButtonStateTrue() {
        viewModelScope.launch {
            _floatingButtonState.value = true
        }

    }

    fun updateFloatingButtonStateFalse() {
        viewModelScope.launch {
            _floatingButtonState.value = false
        }

    }

    fun loadCatsData(userId: String?) {
        loadTasksData(userId)
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

    fun reloadCatsData(userId: String?) {
        viewModelScope.launch {
            //_dataState.value = DataState.Loading
            try {
                val result = DataState.Success(getAllCategoriesUseCase.invoke(userId!!))
                _dataState.value = result
            } catch (e: Exception) {
                _dataState.value = DataState.Error(e)
                println(e.message.toString())
            }
        }
    }

    fun loadTasksData(userId: String?) {

        viewModelScope.launch {
            //_tasksState.value = DataState.Loading
            try {
                val result = DataState.Success(getAllTasksUseCase.invoke(userId!!))
                _tasksState.value = result
            } catch (e: Exception) {
                _tasksState.value = DataState.Error(e)
                println(e.message.toString())
            }
        }
    }

    fun loadTasksInCategory(category: Categories) : List<Task> {
        var result = listOf<Task>()
        viewModelScope.launch {
            result = getTasksInCategoryUseCase(category)
        }
        return result
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
            loadTasksData(supabase.auth.currentSessionOrNull()?.user?.id)
        }
        reloadCatsData(supabase.auth.currentSessionOrNull()?.user?.id)
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
        loadTasksData(supabase.auth.currentSessionOrNull()?.user?.id)
    }

    fun deleteCategory(category: Categories) {
        viewModelScope.launch {
            deleteCategoryUseCase(category)
        }
        reloadCatsData(supabase.auth.currentSessionOrNull()?.user?.id)
    }


}

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<T>(val data: T): DataState<T>()
    data class Error(val message: Exception) : DataState<Nothing>()
}