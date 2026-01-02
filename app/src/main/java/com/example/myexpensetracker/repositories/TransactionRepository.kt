package com.example.myexpensetracker.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.data.mappers.toCategory
import com.example.myexpensetracker.data.mappers.toEntity
import com.example.myexpensetracker.data.mappers.toTransaction
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.localdb.dao.TransactionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.time.TimeSource

class TransactionRepository @Inject constructor
(
    private val transactionDao : TransactionDao
)
{
    fun getTotalBalance(): Flow<Double?> {
        return transactionDao.getTotalBalance()
    }

    fun getTransactionsForPeriod(start: Long, end: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForPeriod(start, end).map { list ->

            list.map { item ->
                Transaction(
                    id = item.transaction.id,
                    amount = item.transaction.amount,
                    date = item.transaction.date,
                    note = item.transaction.note,
                    category = item.category.toCategory()
                )
            }
        }
            .flowOn(Dispatchers.Default)
    }

    fun getRecentTransactions(): Flow<List<Transaction>> {
        return transactionDao.getRecentTransactions(100).map { list ->

            list.map { item ->
                Transaction(
                    id = item.transaction.id,
                    amount = item.transaction.amount,
                    date = item.transaction.date,
                    note = item.transaction.note,
                    category = item.category.toCategory()
                )
            }
        }
            .flowOn(Dispatchers.Default)
    }

    fun getTransactionById(id: Int): Flow<Transaction?> {
        return transactionDao.getTransactionById(id)
            .map { wrapper ->
                wrapper?.let { item ->
                    item.transaction.toTransaction(item.category.toCategory())
                }
            }
    }
    fun getFilteredTransactions(
        startDate: Long,
        endDate: Long,
        types: List<TransactionType>,
        categoryIds: List<Int>
    ): Flow<List<Transaction>> {

        return transactionDao.getFilteredTransactions(
            startDate,
            endDate,
            startDate == 0L,
            types,
            categoryIds,
            categoryIds.isEmpty())
        .map { list ->

            list.map { item ->
                Transaction(
                    id = item.transaction.id,
                    amount = item.transaction.amount,
                    date = item.transaction.date,
                    note = item.transaction.note,
                    category = item.category.toCategory()
                )
            }
        }
        .flowOn(Dispatchers.Default)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getIncomeExpense() : Flow<Pair<Double, Double>>
    {

        val nowTime = Instant.now()
        val monthAgoTime = ZonedDateTime.now()
            .minusMonths(1)
            .toInstant()
        return transactionDao.getTransactionsForPeriod(monthAgoTime.toEpochMilli(), nowTime.toEpochMilli())
            .map { transactions ->
                val income = transactions
                    .filter { it.category.type == TransactionType.INCOME.name }
                    .sumOf { it.transaction.amount }

                val expense = transactions
                    .filter { it.category.type == TransactionType.EXPENSE.name }
                    .sumOf { it.transaction.amount }

                Pair(income, expense)
            }.flowOn(Dispatchers.Default)
    }
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction.toEntity())
    }
}