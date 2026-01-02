package com.example.myexpensetracker.data.models

class Transaction (
    val id: Int,
    val amount: Double,
    val category: Category,
    val date: Long,
    val note: String? = null
)