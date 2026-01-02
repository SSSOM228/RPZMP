package com.example.myexpensetracker.ui.screens.statscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.repositories.TransactionRepository
import com.example.myexpensetracker.utils.ChartData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

data class StatsUiState(
    val selectedType: TransactionType = TransactionType.EXPENSE,
    val donutData: List<ChartData> = emptyList(),
    val barData: List<Pair<String, Float>> = emptyList(),
    val totalAmount: Double = 0.0,
    val isLoading: Boolean = true
)

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _selectedType = MutableStateFlow(TransactionType.EXPENSE)

    @RequiresApi(Build.VERSION_CODES.O)
    val statsUiState: StateFlow<StatsUiState> = combine(
        transactionRepository.getRecentTransactions(), //TODO: all
        _selectedType
    ) { transactions, selectedType ->

        val filteredList = transactions.filter { it.category.type == selectedType }
        val donutData = calculateDonutData(filteredList)
        val totalSum = filteredList.sumOf { it.amount }
        val barData = calculateBarData(filteredList)

        StatsUiState(
            selectedType = selectedType,
            donutData = donutData,
            barData = barData,
            totalAmount = totalSum,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsUiState()
    )

    fun onTypeChanged(type: TransactionType) {
        _selectedType.update { type }
    }

    // --- Private Calculation Helpers ---

    private fun calculateDonutData(transactions: List<Transaction>): List<ChartData> {
        val totalSum = transactions.sumOf { it.amount }
        if (totalSum == 0.0) return emptyList()

        return transactions
            .groupBy { it.category.name } // Map<String, List<Transaction>>
            .map { (categoryName, list) ->
                val categorySum = list.sumOf { it.amount }
                val percentage = (categorySum / totalSum).toFloat()
                val colorHex = list.first().category.colorHex

                ChartData(
                    label = categoryName,
                    value = categorySum.toFloat(),
                    color = Color(colorHex),
                    percentage = percentage
                )
            }
            .sortedByDescending { it.percentage }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateBarData(transactions: List<Transaction>): List<Pair<String, Float>> {
        val zoneId = ZoneId.systemDefault()
        val today = java.time.LocalDate.now()

        val last7Days = (0..6).map { today.minusDays(it.toLong()) }.reversed()

        return last7Days.map { date ->
            val sumForDay = transactions
                .filter { transaction ->
                    val transactionDate = Instant.ofEpochMilli(transaction.date)
                        .atZone(zoneId)
                        .toLocalDate()
                    transactionDate == date
                }
                .sumOf { it.amount }
                .toFloat()

            val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .replaceFirstChar { it.uppercase() }

            dayName to sumForDay
        }
    }
}