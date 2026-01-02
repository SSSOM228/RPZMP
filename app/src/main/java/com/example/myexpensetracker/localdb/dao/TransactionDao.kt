package com.example.myexpensetracker.localdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.localdb.entities.TransactionEntity
import com.example.myexpensetracker.localdb.entities.TransactionWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    suspend fun getAll(): List<TransactionEntity>

    @Transaction
    @Query("SELECT * FROM transactions WHERE id = (:tId)")
    fun getTransactionById(tId: Int): Flow<TransactionWithCategory?>

    @Query("SELECT * FROM transactions WHERE categoryId = (:cId)")
    suspend fun getTransactionsByCategoryId(cId: Int): List<TransactionEntity>

    @Transaction
    @Query("""
    SELECT transactions.* FROM transactions 
    INNER JOIN categories ON transactions.categoryId = categories.id
    WHERE 
        -- 1. Time filter
        (:isAllTime = 1 OR (date >= :startDate AND date <= :endDate))
        AND
        -- 2. Type filter
        categories.type IN (:types)
        AND
        -- 3. Category filter
        (:isAllCategories = 1 OR categoryId IN (:categoryIds))
    ORDER BY date DESC
""")
    fun getFilteredTransactions(
        startDate: Long,
        endDate: Long,
        isAllTime: Boolean,
        types: List<TransactionType>,
        categoryIds: List<Int>,
        isAllCategories: Boolean
    ): Flow<List<TransactionWithCategory>>

    @Query("""
    SELECT COALESCE(SUM(
        CASE 
            WHEN c.type = 'INCOME' THEN t.amount 
            ELSE -t.amount 
        END
    ), 0.0)
    FROM transactions AS t
    INNER JOIN categories AS c ON t.categoryId = c.id
    """)
    fun getTotalBalance(): Flow<Double>

    @Transaction
    @Query("""
        SELECT * FROM transactions WHERE date >= :startDate AND date <= :endDate
    """)
    fun getTransactionsForPeriod(startDate: Long, endDate: Long): Flow<List<TransactionWithCategory>>

    @Transaction
    @Query("""
        SELECT * FROM transactions ORDER BY date DESC LIMIT :limit
    """)
    fun getRecentTransactions(limit: Int): Flow<List<TransactionWithCategory>>

    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity) : Int

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
}