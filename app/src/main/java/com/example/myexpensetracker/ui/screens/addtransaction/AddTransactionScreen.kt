package com.example.myexpensetracker.ui.screens.addtransaction

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.ui.components.AmountInput
import com.example.myexpensetracker.ui.components.CategoryList
import com.example.myexpensetracker.ui.components.DateSelector
import com.example.myexpensetracker.ui.components.LoadingOverlay
import com.example.myexpensetracker.ui.components.TypeSelector
import com.example.myexpensetracker.ui.components.TypeSelectorDefaults.onColoredBackground
import com.example.myexpensetracker.ui.components.TypeSelectorDefaults.onSurfaceBackground
import com.example.myexpensetracker.utils.getIconByName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
)
{
    val uiState by viewModel.addUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.addUiEvent.collect { event ->
            when (event) {
                is AddUiEvent.Success -> {
                    onSaveClick()
                }
                is AddUiEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    val filteredCategories = remember(uiState.allCategories, uiState.isExpense) {
        val targetType = if (uiState.isExpense) TransactionType.EXPENSE else TransactionType.INCOME
        uiState.allCategories.filter { it.type == targetType }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            AddTransactionContent(
                isExpense = uiState.isExpense,
                amount = uiState.amount,
                selectedCategoryId = uiState.selectedCategoryId,
                categoriesSorted = filteredCategories,
                date = uiState.date,
                onTypeChange = viewModel::onTypeChange,
                onAmountChange = viewModel::onAmountChange,
                onCategorySelect = viewModel::onCategorySelect,
                onBackClick = onBackClick,
                onSaveClick = viewModel::onSaveClick,
                onDateChange = viewModel::onDateChange,
                paddingValues = paddingValues,
            )
            LoadingOverlay(isLoading = uiState.isLoading)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionContent(
    isExpense: Boolean,
    amount: String,
    selectedCategoryId: Int?,
    categoriesSorted : List<Category>,
    date: Long,
    onTypeChange: (Boolean) -> Unit,
    onAmountChange: (String) -> Unit,
    onCategorySelect: (Int) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onDateChange: (Long) -> Unit,
    paddingValues: PaddingValues
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isExpense) Color(0xFFE53935) else Color(0xFF43A047),
        animationSpec = tween(durationMillis = 500),
        label = "BgAnimation"
    )

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = date
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateChange(it)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        text = "Select date",
                        modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // --- UPPER PART ---
        ScreenHeader(
            isExpense = isExpense,
            onTypeChange = onTypeChange,
            onBackClick = onBackClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        AmountInput(
            amount = amount,
            onAmountChange = onAmountChange
        )
        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTTOM PART ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            DateSelector(
                date = date,
                onClick = { showDatePicker = true }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Category",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            CategoryList(
                categories = categoriesSorted,
                selectedCategoryId = selectedCategoryId,
                onCategorySelect = onCategorySelect,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray.copy(alpha = 0.2f))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor
                )
            ) {
                Text("Save", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}




@Composable
fun ScreenHeader(
    isExpense: Boolean,
    onTypeChange: (Boolean) -> Unit, // true = Expense, false = Income
    onBackClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isExpense) Color(0xFFE53935) else Color(0xFF43A047),
        animationSpec = tween(durationMillis = 500),
        label = "ColorAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .statusBarsPadding()
            .padding(bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = if (isExpense) "New expense" else "New income",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        TypeSelector(
            isExpense = isExpense,
            onClickExpense = { onTypeChange(true) },
            onClickIncome = { onTypeChange(false) },
            colors = onColoredBackground(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.2f)),
        )
    }
}





// --- Preview ---
@Preview(showBackground = true)
@Composable
fun AddHeaderPreview() {
    MaterialTheme {
        Column {
            ScreenHeader(
                isExpense = true,
                onTypeChange = {},
                onBackClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            ScreenHeader(
                isExpense = false,
                onTypeChange = {},
                onBackClick = {}
            )
//            AmountInput(
//                amount = "",
//                onAmountChange = {}
//            )
        }
    }
}







@Preview(showBackground = true)
@Composable
fun AddTransactionContentPreview() {
    MaterialTheme {
        AddTransactionContent(
            isExpense = true,
            amount = "",
            categoriesSorted = listOf(
                Category(
                    id = 1, name = "Food", iconName = "ic_food",
                    colorHex = 0xFF4CAF50.toLong(), type = TransactionType.EXPENSE
                ),
                Category(
                    id = 2, name = "Salary", iconName = "ic_salary",
                    colorHex = 0xFFFFC107.toLong(), type = TransactionType.INCOME
                ),
                Category(
                    id = 3, name = "Transport", iconName = "ic_transport",
                    colorHex = 0xFF2196F3.toLong(), type = TransactionType.EXPENSE
                )
            ),
            onTypeChange = {},
            onAmountChange = {},
            onBackClick = {},
            onSaveClick = {},
            selectedCategoryId = 2,
            onCategorySelect = { },
            date = 156423,
            onDateChange = {  },
            paddingValues = PaddingValues(0.dp),
        )
    }
}