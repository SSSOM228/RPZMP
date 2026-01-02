package com.example.myexpensetracker.data.mappers

import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.localdb.entities.TransactionEntity


fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = this.id,
        amount = this.amount,
        categoryId = this.category.id,
        date = this.date,
        note = this.note,
    )
}

fun TransactionEntity.toTransaction(category : Category): Transaction {
    return Transaction(
        id = this.id,
        amount = this.amount,
        category = category,
        date = this.date,
        note = this.note,
    )
}