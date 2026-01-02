package com.example.myexpensetracker.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myexpensetracker.localdb.dao.CategoryDao
import com.example.myexpensetracker.localdb.dao.TransactionDao
import com.example.myexpensetracker.localdb.entities.CategoryEntity
import com.example.myexpensetracker.localdb.entities.TransactionEntity

@Database(
    entities = [CategoryEntity::class, TransactionEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

}