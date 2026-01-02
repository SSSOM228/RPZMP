package com.example.myexpensetracker.ui.screens.historytransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.data.models.Transaction
import com.example.myexpensetracker.ui.components.DeleteConfirmationDialog
import com.example.myexpensetracker.ui.components.TransactionDetailDialog
import com.example.myexpensetracker.ui.components.TransactionList
import com.example.myexpensetracker.ui.screens.addtransaction.AddUiEvent
import com.example.myexpensetracker.ui.screens.main.foodCat
import com.example.myexpensetracker.ui.screens.main.lastWeek
import com.example.myexpensetracker.ui.screens.main.salaryCat
import com.example.myexpensetracker.ui.screens.main.today
import com.example.myexpensetracker.ui.screens.main.transportCat
import com.example.myexpensetracker.ui.screens.main.yesterday
import com.example.myexpensetracker.utils.getIconByName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.emptySet
import kotlin.collections.listOf


@Composable
fun HistoryTransactionScreen (
    viewModel : HistoryTransactionViewModel = hiltViewModel(),
    onTransactionClick : (Transaction) -> Unit,
    modifier : Modifier = Modifier
)
{
    val uiState by viewModel.historyUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.historyUiEvent.collect { event ->
            when (event) {
                is HistoryUiEvent.Success -> {
                    //for deleting
                }
                is HistoryUiEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    HistoryTransactionContent(
        transactions = uiState.transactions,
        categories = uiState.allCategories,
        typesVM = uiState.filter.selectedTypes,
        dateRangeVM = uiState.filter.dateRange,
        categoryIdsVM = uiState.filter.selectedCategoryIds,
//        selectedTransaction = selectedTransaction,
        onEditClick = { onTransactionClick(it) },//{ viewModel.updateTransaction(it) },
        onApplyFilters = viewModel::updateFilters,
        onResetFilters = viewModel::resetFilters,
        onDeleteTransaction = viewModel::deleteTransaction,
        modifier = modifier,
    )
}

@Composable
fun HistoryTransactionContent(
    transactions: List<Transaction>,
    categories: List<Category>,
    typesVM: Set<TransactionType>,
    dateRangeVM: Pair<Long, Long>,
    categoryIdsVM: Set<Int>,
//    selectedTransaction: Transaction?,
    onEditClick: (Transaction) -> Unit,
    onApplyFilters: (Pair<Long, Long>, Set<TransactionType>, Set<Int>) -> Unit,
    onResetFilters: () -> Unit,
    onDeleteTransaction: ((Transaction) -> Unit)?,
    modifier: Modifier = Modifier
) {
    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }
    var transactionToDelete by remember { mutableStateOf<Transaction?>(null) }

    var selectedTypes by rememberSaveable { mutableStateOf(typesVM) }
    var currentDateRange by rememberSaveable { mutableStateOf(dateRangeVM) }
    var currentCategoryIds by rememberSaveable { mutableStateOf(categoryIdsVM) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            HistoryHeader(
                dateRange = currentDateRange,
                activeFiltersCount = currentCategoryIds.size,
                onHeaderClick = { showFilterSheet = true }
            )

            TransactionList(
                transactionList = transactions,
                onTransactionClick = { selectedTransaction = it }, //TODO: Refactor
                //onDelete = { item -> transactionToDelete = item },
                modifier = Modifier.weight(1f)
            )
        }
    }
    if (selectedTransaction != null) {
        TransactionDetailDialog(
            transaction = selectedTransaction!!,
            onDismiss = { selectedTransaction = null  },
            onEditClick = {
                onEditClick(selectedTransaction!!)
                selectedTransaction = null
            },
            onDeleteClick = {
                item -> transactionToDelete = item
                selectedTransaction = null
            },
        )
    }
    if (transactionToDelete != null) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDeleteTransaction?.invoke(transactionToDelete!!)
                transactionToDelete = null
            },
            onDismiss = {
                transactionToDelete = null
            }
        )
    }

    if (showFilterSheet) {
        FilterBottomSheet (
            categories = categories,
            selectedDateRange = currentDateRange,
            selectedTypes = selectedTypes,
            selectedCategoryIds = currentCategoryIds,
            onDismiss = { showFilterSheet = false },
            onReset = {
                selectedTypes = setOf(TransactionType.EXPENSE, TransactionType.INCOME)
                currentCategoryIds = setOf(1)
                currentDateRange = Pair(0L, 0L)
                onResetFilters()
            },
            onApply = { dateRange, types, categories ->
                selectedTypes = types
                currentCategoryIds = categories
                currentDateRange = dateRange
                onApplyFilters(dateRange, types, categories)
                showFilterSheet = false
            }
        )
    }
}

@Composable
fun HistoryHeader(
    dateRange: Pair<Long, Long>,
    activeFiltersCount: Int,
    onHeaderClick: () -> Unit
) {
    val formatter = SimpleDateFormat("dd MMM", Locale.ENGLISH)
    val isAllTime = dateRange.first == 0L

    val dateText = if (isAllTime) {
        "All the time"
    } else {
        "${formatter.format(Date(dateRange.first))} - ${formatter.format(Date(dateRange.second))}"
    }
    Surface(
        onClick = onHeaderClick,
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Timespan",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = dateText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )

                if (activeFiltersCount > 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "+$activeFiltersCount",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangeSelector(
    startDate: Long,
    endDate: Long,
    onDateRangeSelected: (Long, Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var showModal by remember { mutableStateOf(false) }

    val formatter = SimpleDateFormat("dd MMM", Locale.ENGLISH)
    val dateText = if (startDate == 0L || endDate == 0L) {
        "All time"
    } else {
        "${formatter.format(Date(startDate))} - ${formatter.format(Date(endDate))}"
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .clickable { showModal = true }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Date Range",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
//                Text(
//                    text = "Timespan",
//                    style = MaterialTheme.typography.labelSmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
                Text(
                    text = dateText,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }



    if (showModal) {
        val initialStart = if (startDate == 0L) System.currentTimeMillis() else startDate
        val initialEnd = if (endDate == 0L) System.currentTimeMillis() else endDate

        val datePickerState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = initialStart,
            initialSelectedEndDateMillis = initialEnd
        )

        DatePickerDialog(
            onDismissRequest = { showModal = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val start = datePickerState.selectedStartDateMillis
                        val end = datePickerState.selectedEndDateMillis
                        if (start != null && end != null) {
                            onDateRangeSelected(start, end)
                        }
                        showModal = false
                    },
                    enabled = datePickerState.selectedEndDateMillis != null
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showModal = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DateRangePicker(
                state = datePickerState,
                title = {
                    Text(
                        text = "Select a range",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold
                    )
                },
                headline = {
                },
                modifier = Modifier.height(500.dp)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    categories: List<Category>,
    selectedDateRange: Pair<Long, Long>,
    selectedTypes: Set<TransactionType>,
    selectedCategoryIds: Set<Int>,
    onApply: (Pair<Long, Long>, Set<TransactionType>, Set<Int>) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        FilterBottomSheetContent(
            categories = categories,
            selectedDateRange = selectedDateRange,
            selectedTypes = selectedTypes,
            selectedCategoryIds = selectedCategoryIds,
            onApply = onApply,
            onDismiss = onDismiss,
            onReset = onReset,
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetContent(
    categories: List<Category>,
    selectedDateRange: Pair<Long, Long>,
    selectedTypes: Set<TransactionType>,
    selectedCategoryIds: Set<Int>,
    onApply: (Pair<Long, Long>, Set<TransactionType>, Set<Int>) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit
) {

    var currentTypes by remember(selectedTypes) { mutableStateOf(selectedTypes) }
    var currentCategories by remember(selectedCategoryIds) { mutableStateOf(selectedCategoryIds) }
    var currentDateRange by remember(selectedDateRange) { mutableStateOf(selectedDateRange) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
            .heightIn(max = 600.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filters",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = {
                    currentTypes = setOf(TransactionType.EXPENSE, TransactionType.INCOME)
                    currentCategories = emptySet()
                    onReset()
                },
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Reset",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))


        // ====== 1. DATE ======
        Text("Timespan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        DateRangeSelector(
            startDate = currentDateRange.first,
            endDate = currentDateRange.second,
            onDateRangeSelected = { a,b -> currentDateRange = Pair(a,b) }
        )

        Spacer(modifier = Modifier.height(24.dp))




        // ====== 2. TYPE ======
        Text(
            text = "Type of operation",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FilterTypeButton(
                text = "Expenses",
                color = Color(0xFFE53935),
                isSelected = currentTypes.contains(TransactionType.EXPENSE),
                onClick = {
                    currentTypes = toggleType(currentTypes, TransactionType.EXPENSE)
                },
                modifier = Modifier.weight(1f)
            )

            FilterTypeButton(
                text = "Income",
                color = Color(0xFF43A047),
                isSelected = currentTypes.contains(TransactionType.INCOME),
                onClick = {
                    currentTypes = toggleType(currentTypes, TransactionType.INCOME)
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))



        // ====== 3. CATEGORY ======
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(categories) { category ->
                FilterCategoryItem(
                    category = category,
                    isSelected = currentCategories.contains(category.id),
                    onClick = {
                        currentCategories = if (currentCategories.contains(category.id)) {
                            currentCategories - category.id
                        } else {
                            currentCategories + category.id
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onApply(currentDateRange, currentTypes, currentCategories) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Apply filters", fontSize = 18.sp)
        }
    }
}
fun toggleType(current: Set<TransactionType>, target: TransactionType): Set<TransactionType> {
    return if (current.contains(target)) {
        if (current.size > 1) current - target else current
    } else {
        current + target
    }
}
@Composable
fun FilterCategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val categoryColor = Color(category.colorHex)
    val shape = RoundedCornerShape(16.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(shape)
            .background(
                if (isSelected) categoryColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(
                    alpha = 0.3f
                )
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) categoryColor else Color.Transparent,
                shape = shape
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(categoryColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = getIconByName(category.iconName),
                contentDescription = null,
                tint = categoryColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isSelected) categoryColor else Color.Transparent)
                .border(
                    width = 2.dp,
                    color = if (isSelected) categoryColor else Color.Gray.copy(alpha = 0.5f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun FilterTypeButton(
    text: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) color.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(
                    alpha = 0.3f
                )
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) color else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) color else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


//===========================
// ======== Preview ========
//===========================

val sampleCategories = listOf(
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
fun HistoryHeaderPreview() {
    MaterialTheme {
        HistoryHeader(
            dateRange = Pair(0L,86399999L),
            activeFiltersCount = 0,
            onHeaderClick = {},
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HistoryTransactionContentPreview() {
//    MaterialTheme {
//        HistoryTransactionContent(
//            transactions = sampleTransactions,
//            categories = sampleCategories,
//            onSelectedTransactionChange = {}
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun FilterBottomSheetPreview() {
    MaterialTheme {
        FilterBottomSheetContent(
            categories = sampleCategories,
            selectedDateRange = Pair(86399999L,86399999L),
            selectedTypes = setOf(),
            selectedCategoryIds = setOf(1),
            onApply = {a,b,c ->},
            onDismiss = {},
            onReset = {}
        )
    }
}