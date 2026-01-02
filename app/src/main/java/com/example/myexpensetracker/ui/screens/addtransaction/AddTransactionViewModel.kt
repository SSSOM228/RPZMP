package com.example.myexpensetracker.ui.screens.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.repositories.CategoryRepository
import com.example.myexpensetracker.repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddTransactionState(
    val amount: String = "",
    val note: String = "",
    val date: Long = System.currentTimeMillis(),
    val isExpense: Boolean = true,
    val selectedCategoryId: Int? = null,
    val allCategories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface AddUiEvent {
    object Success : AddUiEvent
    data class ShowError(val message: String) : AddUiEvent
}

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _addUiState = MutableStateFlow(AddTransactionState())
    val addUiState = _addUiState.asStateFlow()


    private val _addUiEvent = Channel<AddUiEvent>()
    val addUiEvent = _addUiEvent.receiveAsFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _addUiState.update { it.copy(isLoading = true) }
            categoryRepository.getAllCategories().collect { categories ->
                _addUiState.update { it.copy(
                    allCategories = categories,
                    isLoading = false
                ) }
            }
        }
    }

    fun onAmountChange(newValue: String) {
        if (newValue.all { it.isDigit() || it == '.' } && newValue.count { it == '.' } <= 1) {
            _addUiState.update { it.copy(amount = newValue) }
        }
    }

    fun onTypeChange(isExpense: Boolean) {
        _addUiState.update {
            it.copy(
                isExpense = isExpense,
                selectedCategoryId = null
            )
        }
    }

    fun onCategorySelect(categoryId: Int) {
        _addUiState.update { it.copy(selectedCategoryId = categoryId) }
    }

    fun onDateChange(newDate: Long) {
        _addUiState.update { it.copy(date = newDate) }
    }

    fun onSaveClick() {
        viewModelScope.launch {
            val state = _addUiState.value
            val amountDouble = state.amount.toDoubleOrNull()

            if (amountDouble == null || amountDouble <= 0) {
                _addUiEvent.send(AddUiEvent.ShowError("Incorrect sum"))
                return@launch
            }

            if (state.selectedCategoryId == null) {
                _addUiEvent.send(AddUiEvent.ShowError("No category"))
                return@launch
            }

            val transaction = Transaction(
                amount = amountDouble,
                category = state.allCategories.find { category -> category.id == state.selectedCategoryId }!!,
                date = state.date,
                note = state.note,
                id = 0
            )

            _addUiState.update { it.copy(isLoading = true) }
            try {
                transactionRepository.insertTransaction(transaction)
                _addUiEvent.send(AddUiEvent.Success)
            } catch (e: Exception) {
                _addUiEvent.send(AddUiEvent.ShowError("Error: ${e.message}"))
            } finally {
                _addUiState.update { it.copy(isLoading = false) }
            }
        }
    }
}