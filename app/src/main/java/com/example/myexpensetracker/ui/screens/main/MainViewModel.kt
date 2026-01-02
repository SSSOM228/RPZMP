package com.example.myexpensetracker.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.repositories.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class MainState(
    var isLoading: Boolean = false,
    var totalBalance: Double = 0.0,
    var income: Double = 0.0,
    var expense: Double = 0.0,
    var recentTransactions: List<Transaction> = emptyList()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
): ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val mainUiState: StateFlow<MainState> = combine(
        transactionRepository.getTotalBalance(),
        transactionRepository.getIncomeExpense(),
        transactionRepository.getRecentTransactions()
    ) { balance, incomeExpense, recentList ->
        val monthlyIncome = incomeExpense.first
        val monthlyExpense = incomeExpense.second
        MainState(
            isLoading = false,
            totalBalance = balance ?: 0.0,
            income = incomeExpense.first,
            expense = incomeExpense.second,
            recentTransactions = recentList
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainState(isLoading = true)
    )
}

