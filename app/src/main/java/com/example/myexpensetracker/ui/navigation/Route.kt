package com.example.myexpensetracker.ui.navigation

sealed class Route(val name: String) {
    object Main : Route("main_screen")
    object AddTransaction : Route("add_transaction_screen")
    object History : Route("history_screen")
    object Stats : Route("stats_screen")
    object CategoryManage : Route("category_manage_screen")
    object EditTransaction : Route("edit_transaction/{transactionId}") {
        fun createRoute(transactionId: Int) = "edit_transaction/$transactionId"
    }
}