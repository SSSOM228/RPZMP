package com.example.myexpensetracker.utils

import androidx.compose.ui.graphics.Color

data class ChartData(
    val label: String,
    val value: Float,
    val color: Color,
    val percentage: Float
)