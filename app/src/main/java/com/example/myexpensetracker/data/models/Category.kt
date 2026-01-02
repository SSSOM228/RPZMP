package com.example.myexpensetracker.data.models

import com.example.myexpensetracker.data.enums.TransactionType

class Category (
    val id: Int,
    val name: String,
    val iconName: String,
    val colorHex: Long,
    val type: TransactionType,
    val isArchived: Boolean = false
)