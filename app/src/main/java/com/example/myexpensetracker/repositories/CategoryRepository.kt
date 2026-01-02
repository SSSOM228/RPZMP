package com.example.myexpensetracker.repositories

import androidx.lifecycle.ViewModel
import com.example.myexpensetracker.data.mappers.toCategory
import com.example.myexpensetracker.data.mappers.toEntity
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.localdb.dao.CategoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class CategoryRepository @Inject constructor
(
    private val categoryDao : CategoryDao
) : ViewModel() {

    fun getAllCategories() : Flow<List<Category>>
    {
        return categoryDao.getAll().map { list ->
            list.map { item ->
                item.toCategory()
            }
        }
    }
    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category.toEntity())
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category.toEntity())
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.toEntity())
    }
}