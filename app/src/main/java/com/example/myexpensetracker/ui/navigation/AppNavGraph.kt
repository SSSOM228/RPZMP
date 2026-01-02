package com.example.myexpensetracker.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetracker.components.StatsScreen
import com.example.myexpensetracker.ui.screens.addtransaction.AddTransactionScreen
import com.example.myexpensetracker.ui.screens.categorymanage.CategoryManageScreen
import com.example.myexpensetracker.ui.screens.edittransaction.EditTransactionScreen
import com.example.myexpensetracker.ui.screens.historytransaction.HistoryTransactionScreen
import com.example.myexpensetracker.ui.screens.main.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.Main.name
    ) {
        composable(route = Route.Main.name) {
            MainScreen(
                onAddClick = {
                    navController.navigate(Route.AddTransaction.name)
                }
            )
        }

        composable(route = Route.AddTransaction.name) {
            AddTransactionScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { navController.popBackStack() }
            )
        }

        composable(route = Route.History.name) {
            HistoryTransactionScreen(
                onTransactionClick = { transaction ->
                    navController.navigate(Route.EditTransaction.createRoute(transaction.id))
                }
            )
        }

        composable(route = Route.Stats.name) {
            StatsScreen(
            )
        }

        composable(route = Route.CategoryManage.name) {
            CategoryManageScreen(
            )
        }

        composable(
            route = Route.EditTransaction.name,
            arguments = listOf(
                navArgument("transactionId") { type = NavType.IntType }
            )
        ){
            EditTransactionScreen (
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}