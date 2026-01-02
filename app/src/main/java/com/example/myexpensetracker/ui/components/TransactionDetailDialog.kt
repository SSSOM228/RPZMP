package com.example.myexpensetracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.ui.screens.main.foodCat
import com.example.myexpensetracker.ui.screens.main.today
import com.example.myexpensetracker.utils.getIconByName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionDetailDialog(
    transaction: Transaction,
    onDismiss: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: (Transaction) -> Unit
) {
    val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.ENGLISH)
    val color = Color(transaction.category.colorHex)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = color.copy(alpha = 0.2f),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = getIconByName(transaction.category.iconName),
                            contentDescription = transaction.category.name,
                            tint = color,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = transaction.category.name, maxLines = 1)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "$${String.format(Locale.ENGLISH, "%.0f", transaction.amount)}",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = if (transaction.category.type == TransactionType.EXPENSE) Color(0xFFE53935) else Color(0xFF43A047)
                )

                HorizontalDivider()

                Column {
                    Text("Date", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                    Text(formatter.format(Date(transaction.date)), style = MaterialTheme.typography.bodyLarge)
                }

                if (!transaction.note.isNullOrBlank()) {
                    Column {
                        Text("Note", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                        Text(transaction.note, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onEditClick()
                onDismiss()
            }) {
                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Edit")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDeleteClick(transaction)
                onDismiss()
            }, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Delete")
            }
        }
    )
}

@Preview
@Composable
fun TransactionDetailDialogPreview()
{
    MaterialTheme {
        TransactionDetailDialog(
            transaction = Transaction(
                id = 1, amount = 150.0, category = Category(
                    id = 1, name = "Food", iconName = "ic_food",
                    colorHex = 0xFF4CAF50.toLong(), type = TransactionType.EXPENSE
                ), date = today, note = "Обід у Пузатій Хаті",
            ),
            onDismiss = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}