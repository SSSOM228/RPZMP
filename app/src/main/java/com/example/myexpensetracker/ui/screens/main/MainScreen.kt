package com.example.myexpensetracker.ui.screens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.ui.components.TransactionList
import com.example.myexpensetracker.ui.theme.Primary
import com.example.myexpensetracker.ui.theme.Secondary
import com.example.myexpensetracker.utils.formatDateHeader
import com.example.myexpensetracker.utils.getIconByName
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen (
    viewModel: MainViewModel = hiltViewModel(),
    onAddClick: () -> Unit,
    //onTransactionClick : (Transaction) -> Unit,
    modifier: Modifier = Modifier,
)
{
    val uiState by viewModel.mainUiState.collectAsState()
    MainContent(
        totalBalance = uiState.totalBalance,
        income = uiState.income,
        expense = uiState.expense,
        transactionList = uiState.recentTransactions,
        onAddClick = onAddClick,
        onSelectedTransactionChange = {},   //do nothing)
        modifier = modifier
    )
}

@Composable
fun MainContent(
    totalBalance: Double,
    income: Double,
    expense: Double,
    transactionList: List<Transaction>,
    onAddClick: () -> Unit,
    onSelectedTransactionChange : (Transaction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
    { padding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            MainScreenHeader(totalBalance, income, expense)
            Text(
                text = "Recent transactions",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            TransactionList(
                transactionList,
                onTransactionClick = { onSelectedTransactionChange(it) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun MainScreenHeader(
    totalBalance: Double,
    income: Double,
    expense: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Secondary.copy(alpha = 0.15f),
            contentColor = Secondary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total balance",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$ ${String.format(Locale.ENGLISH,"%.0f", totalBalance)}",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FinanceStatItem(
                    label = "Incomes",
                    amount = income,
                    icon = Icons.Default.ArrowUpward,
                    iconColor = Color(0xFF4CAF50),
                    backgroundColor = Color(0xFF4CAF50).copy(0.45f)
                )

                FinanceStatItem(
                    label = "Expenses",
                    amount = expense,
                    icon = Icons.Default.ArrowDownward,
                    iconColor = Color(0xFFF44336),
                    backgroundColor = Color(0xFFF44336).copy(0.45f)
                )
            }
        }
    }
}

@Composable
fun FinanceStatItem(
    label: String,
    amount: Double,
    icon: ImageVector,
    iconColor: Color,
    backgroundColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = backgroundColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            Text(
                text = "$ ${String.format(Locale.ENGLISH, "%.0f", amount)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun TransactionDateHeader(date: String) {
    Text(
        text = date.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        //color = MaterialTheme.colorScheme.background,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background) //MaterialTheme.colorScheme.surface
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun TransactionDetailDialog(
    transaction: Transaction,
    onDismiss: () -> Unit
) {
    val category = transaction.category

    val isExpense = category.type == TransactionType.EXPENSE
    val amountColor = if (isExpense) Color(0xFFE53935) else Color(0xFF43A047)

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = getIconByName(category.iconName),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = Color(category.colorHex)
            )
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = category.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "₴${String.format(Locale.ENGLISH,"%.2f", transaction.amount)}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = amountColor,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                RowDetail(label = "Date", value = formatDateHeader(transaction.date))

                if (!transaction.note.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    RowDetail(label = "Note", value = transaction.note)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
@Composable
fun RowDetail(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

// == Preview ==
val today = System.currentTimeMillis()
val yesterday = today - 86400000L
val lastWeek = today - (86400000L * 5)

val foodCat = Category(
    id = 1, name = "Food", iconName = "ic_food",
    colorHex = 0xFF4CAF50.toLong(), type = TransactionType.EXPENSE
)
val salaryCat = Category(
    id = 2, name = "Salary", iconName = "ic_salary",
    colorHex = 0xFFFFC107.toLong(), type = TransactionType.INCOME
)
val transportCat = Category(
    id = 3, name = "Transport", iconName = "ic_transport",
    colorHex = 0xFF2196F3.toLong(), type = TransactionType.EXPENSE
)

val sampleTransactions = listOf(
    Transaction(
        id = 1, amount = 150.0, category = foodCat, date = today, note = "Обід у Пузатій Хаті",
    ),
    Transaction(
        id = 2, amount = 25000.0, category = salaryCat, date = today, note = "Аванс",
    ),

    Transaction(
        id = 3, amount = 45.0, category = transportCat, date = yesterday, note = "Маршрутка",
    ),

    Transaction(
        id = 4, amount = 500.0, category = foodCat, date = lastWeek, note = "Сільпо",
    )
)
@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    MaterialTheme {
        MainContent(
            totalBalance = 12450.00,
            income = 24000.00,
            expense = 11550.00,
            transactionList = sampleTransactions,
            onAddClick = {},
            onSelectedTransactionChange = { }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    MaterialTheme {
        MainScreenHeader(
            totalBalance = 12450.00,
            income = 24000.00,
            expense = 11550.00
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDetailDialogPreview() {
    MaterialTheme {
        TransactionDetailDialog(
            transaction = sampleTransactions[0],
            onDismiss = {  }
        )
    }
}
