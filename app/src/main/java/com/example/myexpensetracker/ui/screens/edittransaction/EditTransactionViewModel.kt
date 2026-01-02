package com.example.myexpensetracker.ui.screens.edittransaction

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.repositories.CategoryRepository
import com.example.myexpensetracker.repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditUiState(
    val amount: String = "",
    val note: String = "",
    val date: Long = System.currentTimeMillis(),
    val selectedCategory: Category? = null,
    val allCategories: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val isExpense: Boolean = true
)

sealed interface EditUiEvent {
    object Success : EditUiEvent
    data class ShowError(val message: String) : EditUiEvent
}

@HiltViewModel
class EditTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val transactionId: Int = checkNotNull(savedStateHandle["transactionId"])

    private val _editUiState = MutableStateFlow(EditUiState())
    val editUiState = _editUiState.asStateFlow()


    private val _editUiEvent = Channel<EditUiEvent>()
    val editUiEvent = _editUiEvent.receiveAsFlow()

    init {
        loadTransactionData()
    }

    private fun loadTransactionData() {
        viewModelScope.launch {
            _editUiState.update { it.copy(isLoading = true) }

            try {
                val transactionDeferred = async { transactionRepository.getTransactionById(transactionId).first() }
                val categoriesDeferred = async { categoryRepository.getAllCategories().first()}

                val transaction = transactionDeferred.await()
                val categories = categoriesDeferred.await()

                if (transaction != null) {
                    _editUiState.update {
                        it.copy(
                            amount = if (transaction.amount % 1.0 == 0.0) {
                                transaction.amount.toInt().toString() // "500" замість "500.0"
                            } else {
                                transaction.amount.toString()
                            },
                            note = transaction.note ?: "",
                            date = transaction.date,
                            selectedCategory = transaction.category,
                            allCategories = categories,
                            isExpense = transaction.category.type == TransactionType.EXPENSE,
                            isLoading = false
                        )
                    }
                }
                else {
                    _editUiEvent.send(EditUiEvent.ShowError("Transaction is not found"))
                    _editUiState.update { it.copy(isLoading = false) }
                }
            } catch (e: Exception) {
                _editUiEvent.send(EditUiEvent.ShowError("Error: ${e.message}"))
                _editUiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onAmountChange(newValue: String) {
        if (newValue.all { it.isDigit() || it == '.' } && newValue.count { it == '.' } <= 1) {
            _editUiState.update { it.copy(amount = newValue) }
        }
    }

    fun onNoteChange(newValue: String) {
        _editUiState.update { it.copy(note = newValue) }
    }

    fun onDateChange(newDate: Long) {
        _editUiState.update { it.copy(date = newDate) }
    }

    fun onCategorySelect(category: Category) {
        _editUiState.update { it.copy(selectedCategory = category) }
    }

    fun onEditClick() {
        viewModelScope.launch {
            val state = _editUiState.value
            val amountDouble = state.amount.toDoubleOrNull()

            if (amountDouble == null || amountDouble <= 0) {
                _editUiEvent.send(EditUiEvent.ShowError("Invalid amount"))
                return@launch
            }

            if (state.selectedCategory == null) {
                _editUiEvent.send(EditUiEvent.ShowError("No category"))
                return@launch
            }

            val updatedTransaction = Transaction(
                id = transactionId,
                amount = amountDouble,
                date = state.date,
                note = state.note,
                category = state.selectedCategory
            )

            try {
                transactionRepository.updateTransaction(updatedTransaction)
                _editUiEvent.send(EditUiEvent.Success)
            } catch (e: Exception) {
                _editUiEvent.send(EditUiEvent.ShowError("Error: ${e.message}"))
            }
        }
    }
}