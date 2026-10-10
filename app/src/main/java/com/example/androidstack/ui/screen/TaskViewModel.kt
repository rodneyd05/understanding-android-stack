package com.example.androidstack.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstack.data.Task
import com.example.androidstack.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository = TaskRepository()
): ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val _newTaskTitle = MutableStateFlow("")
    val newTaskTitle = _newTaskTitle.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        collectTask()
    }

    private fun collectTask() {
        viewModelScope.launch {
            try {
                repository.collectTasks().collect { tasks ->
                    _tasks.value = tasks
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load tasks"
            }
        }
    }

    fun onTitleChange(title: String) {
        _newTaskTitle.value = title
    }

    fun addTask() {
        val title = _newTaskTitle.value.trim()


        if (title.isBlank()) return

        viewModelScope.launch {
            try {
                _error.value = null
                repository.addTask(title)
                _newTaskTitle.value = ""
            } catch (e: Exception) {
                _error.value = e.message ?: "Unable to add task"
            }
        }
    }
}