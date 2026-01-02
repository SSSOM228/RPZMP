package com.example.myexpensetracker.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.util.*

fun getIconByName(name: String): ImageVector {
    return when (name) {"ic_food" -> Icons.Rounded.Restaurant
        "ic_salary" -> Icons.Rounded.AttachMoney
        "ic_transport" -> Icons.Rounded.DirectionsBus
        "ic_home" -> Icons.Rounded.Home
        "ic_shopping" -> Icons.Rounded.ShoppingCart
        "ic_entertainment" -> Icons.Rounded.Movie // Or Icons.Rounded.Theaters
        "ic_game" -> Icons.Rounded.SportsEsports
        "ic_health" -> Icons.Rounded.MedicalServices // Or Icons.Rounded.LocalHospital
        "ic_education" -> Icons.Rounded.School
        "ic_gift" -> Icons.Rounded.CardGiftcard
        "ic_sport" -> Icons.Rounded.FitnessCenter // Or Icons.Rounded.SportsSoccer
        "ic_pets" -> Icons.Rounded.Pets
        "ic_travel" -> Icons.Rounded.Flight
        else -> Icons.Default.Category
    }
}

fun formatDateHeader(timestamp: Long): String {
    val date = Date(timestamp)
    val now = Date()
    val visualDateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    val comparisonFormatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    return when {
        comparisonFormatter.format(date) == comparisonFormatter.format(now) -> "Today"
        comparisonFormatter.format(date) == comparisonFormatter.format(Date(now.time - 86400000)) -> "Yesterday"
        else -> visualDateFormatter.format(date)
    }
}