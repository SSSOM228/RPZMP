package com.example.myexpensetracker.localdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myexpensetracker.localdb.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE isArchived = 0")
    fun getActiveCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = (:tId)")
    fun getCategoryById(tId: Int): CategoryEntity

    @Query("""
        SELECT c.name, SUM(t.amount) as total 
        FROM transactions t
        INNER JOIN categories c ON t.categoryId = c.id
        WHERE t.date BETWEEN :startDate AND :endDate
        GROUP BY c.name
    """)
    fun getSpendingByCategory(startDate: Long, endDate: Long): Flow<List<CategoryTuple>>

    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity) : Int

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
}

data class CategoryTuple(
    val name: String,
    val total: Double
)