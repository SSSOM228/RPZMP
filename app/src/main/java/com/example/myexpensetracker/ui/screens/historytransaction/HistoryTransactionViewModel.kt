package com.example.myexpensetracker.ui.screens.historytransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.repositories.CategoryRepository
import com.example.myexpensetracker.repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryFilter(
    var dateRange: Pair<Long, Long> = Pair(0L, 0L),
    var selectedTypes: Set<TransactionType> = setOf(TransactionType.INCOME, TransactionType.EXPENSE),
    var selectedCategoryIds: Set<Int> = emptySet()
)

data class HistoryUiState(
    var filter: HistoryFilter,
    var transactions: List<Transaction> = emptyList(),
    var allCategories: List<Category> = emptyList(),
    var isLoading: Boolean = false
)
sealed interface HistoryUiEvent {
    object Success : HistoryUiEvent
    data class ShowError(val message: String) : HistoryUiEvent
}

@HiltViewModel
class HistoryTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private val _currentFilter = MutableStateFlow(HistoryFilter())

    @OptIn(ExperimentalCoroutinesApi::class)
    val historyUiState: StateFlow<HistoryUiState> = _currentFilter
        .flatMapLatest { filter ->
            combine(
        transactionRepository.getFilteredTransactions(
            startDate = _currentFilter.value.dateRange.first,
            endDate = _currentFilter.value.dateRange.second,
            types = _currentFilter.value.selectedTypes.toList(),
            categoryIds = _currentFilter.value.selectedCategoryIds.toList()
            ),
        categoryRepository.getAllCategories()
        ) { transactions, categories ->
                HistoryUiState(
                    filter = _currentFilter.value,
                    transactions = transactions,
                    allCategories = categories,
                    isLoading = false
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HistoryUiState(filter = HistoryFilter(), isLoading = true)
        )

    private val _historyUiEvent = Channel<HistoryUiEvent>()
    val historyUiEvent = _historyUiEvent.receiveAsFlow()

    fun updateFilters(
        dateRange: Pair<Long, Long>,
        types: Set<TransactionType>,
        categoryIds: Set<Int>
    ) {
        _currentFilter.value = HistoryFilter(
            dateRange = dateRange,
            selectedTypes = types,
            selectedCategoryIds = categoryIds
        )
    }

    fun resetFilters() {
        _currentFilter.value = HistoryFilter()
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                transactionRepository.updateTransaction(transaction)
                _historyUiEvent.send(HistoryUiEvent.Success)

            } catch (e: Exception) {
                _historyUiEvent.send(HistoryUiEvent.ShowError("Update error: ${e.message}"))
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                transactionRepository.deleteTransaction(transaction)
                _historyUiEvent.send(HistoryUiEvent.Success)

            } catch (e: Exception) {
                _historyUiEvent.send(HistoryUiEvent.ShowError("Delete error: ${e.message}"))
            }
        }
    }
}


