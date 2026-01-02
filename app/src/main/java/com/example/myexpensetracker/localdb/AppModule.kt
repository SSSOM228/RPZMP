package com.example.myexpensetracker.localdb
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.localdb.dao.CategoryDao
import com.example.myexpensetracker.localdb.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "expense_tracker_db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO categories (name, iconName, colorHex, type, isArchived) VALUES ('Food', 'ic_food', ${0xFF4CAF50}, '${TransactionType.EXPENSE.name}', 0)")
                    db.execSQL("INSERT INTO categories (name, iconName, colorHex, type, isArchived) VALUES ('Transport', 'ic_transport', ${0xFF2196F3}, '${TransactionType.EXPENSE.name}', 0)")
                    db.execSQL("INSERT INTO categories (name, iconName, colorHex, type, isArchived) VALUES ('Entertainment', 'ic_game', ${0xFF9C27B0}, '${TransactionType.EXPENSE.name}', 0)")
                    db.execSQL("INSERT INTO categories (name, iconName, colorHex, type, isArchived) VALUES ('Salary', 'ic_salary', ${0xFFFFC107}, '${TransactionType.INCOME.name}', 0)")
                }
            })
            .build()
    }

    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao {
        return appDatabase.transactionDao()
    }

    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }
}