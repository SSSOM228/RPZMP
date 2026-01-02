package com.example.myexpensetracker.ui.screens.categorymanage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryManageState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)
sealed interface CategoryUiEvent {
    data class ShowError(val message: String) : CategoryUiEvent
    object Success : CategoryUiEvent
}

@HiltViewModel
class CategoryManageViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categoryUiState = MutableStateFlow(CategoryManageState())
    val categoryUiState: StateFlow<CategoryManageState> = _categoryUiState.asStateFlow()

    private val _categoryUiEvent = Channel<CategoryUiEvent>()
    val categoryUiEvent = _categoryUiEvent.receiveAsFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoryUiState.update { it.copy(isLoading = true) }
            categoryRepository.getAllCategories()
                .catch { e ->
                    _categoryUiState.update { it.copy(isLoading = false) }
                    _categoryUiEvent.send(CategoryUiEvent.ShowError("Error: ${e.message}"))
                }
                .collect { list ->
                    _categoryUiState.update {
                        it.copy(
                            categories = list,
                            isLoading = false
                        )
                    }
                }
        }
    }

    // --- User Actions ---
    private fun performOperation(
        successMessage: String,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            _categoryUiState.update { it.copy(isLoading = true) }
            try {
                block()
                _categoryUiEvent.send(CategoryUiEvent.Success)
                _categoryUiEvent.send(CategoryUiEvent.ShowError(successMessage))
            } catch (e: Exception) {
                _categoryUiEvent.send(CategoryUiEvent.ShowError("Error: ${e.message}"))
            } finally {
                _categoryUiState.update { it.copy(isLoading = false) }
            }
        }
    }
    fun addCategory(category: Category) {
        performOperation("Category was added successfully!") {
            categoryRepository.insertCategory(category)
        }
    }

    fun updateCategory(category: Category) {
        performOperation("Category was edited successfully!") {
            categoryRepository.updateCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        performOperation("Category was deleted successfully!") {
            categoryRepository.deleteCategory(category)
        }
    }
}

