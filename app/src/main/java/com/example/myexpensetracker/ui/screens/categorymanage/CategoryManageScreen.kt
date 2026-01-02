package com.example.myexpensetracker.ui.screens.categorymanage

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.data.models.Category
import com.example.myexpensetracker.ui.components.LoadingOverlay
import com.example.myexpensetracker.ui.components.TypeSelector
import com.example.myexpensetracker.ui.components.TypeSelectorDefaults.onSurfaceBackground
import com.example.myexpensetracker.utils.CategoryDefaults
import com.example.myexpensetracker.utils.getIconByName



@Composable
fun CategoryManageScreen(
    viewModel: CategoryManageViewModel = hiltViewModel(),
    modifier : Modifier = Modifier
)
{
    val uiState by viewModel.categoryUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var showDialog by remember { mutableStateOf(false) }
    var categoryToEdit by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(Unit) {
        viewModel.categoryUiEvent.collect { event ->
            when (event) {
                is CategoryUiEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is CategoryUiEvent.Success -> {
                    showDialog = false
                    categoryToEdit = null
                }
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CategoryManageContent(
            categories = uiState.categories,
            isLoading = uiState.isLoading,
            snackbarHostState = snackbarHostState,
            showDialog = showDialog,
            categoryToEdit = categoryToEdit,
            onOpenDialog = { category ->
                categoryToEdit = category
                showDialog = true
            },
            onCloseDialog = { showDialog = false },
            onAddCategory = viewModel::addCategory,
            onUpdateCategory = viewModel::updateCategory,
            onDeleteCategory = viewModel::deleteCategory,
        )
        LoadingOverlay(isLoading = uiState.isLoading)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryManageContent(
    categories: List<Category>,
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    showDialog: Boolean,
    categoryToEdit: Category?,
    onOpenDialog: (Category?) -> Unit,
    onCloseDialog: () -> Unit,
    onAddCategory: (Category) -> Unit,
    onUpdateCategory: (Category) -> Unit,
    onDeleteCategory: (Category) -> Unit
) {
    var currentType by remember { mutableStateOf(TransactionType.EXPENSE) }

    val filteredCategories = categories.filter { it.type == currentType }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .statusBarsPadding()
            ) {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                TypeSelector(
                    isExpense = currentType == TransactionType.EXPENSE,
                    onClickExpense = { currentType = TransactionType.EXPENSE },
                    onClickIncome = { currentType = TransactionType.INCOME },
                    colors = onSurfaceBackground(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onOpenDialog(null) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(filteredCategories) { category ->
                CategoryManageItem(
                    category = category,
                    onClick = { onOpenDialog(category) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 72.dp),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }

    if (showDialog) {
        CategoryDialog(
            category = categoryToEdit,
            initialType = currentType,
            isLoading = isLoading,
            onDismiss = onCloseDialog,
            onSave = { category ->
                if (category.id == 0) onAddCategory(category) else onUpdateCategory(category)
                //onCloseDialog()
            },
            onDelete = { category ->
                onDeleteCategory(category)
                //onCloseDialog()
            }
        )
    }
}

@Composable
fun CategoryManageItem(
    category: Category,
    onClick: () -> Unit
) {
    val color = Color(category.colorHex.toInt())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = getIconByName(category.iconName),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = category.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium, // Трохи легший шрифт
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Edit",
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
            modifier = Modifier.size(24.dp)
        )
    }
}


//@Composable
//fun SegmentButton(
//    text: String,
//    isSelected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val containerColor by animateColorAsState(
//        targetValue = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
//        label = "color"
//    )
//    val shadowElevation by animateDpAsState(
//        targetValue = if (isSelected) 2.dp else 0.dp,
//        label = "shadow"
//    )
//
//    Box(
//        modifier = modifier
//            .fillMaxHeight()
//            .shadow(shadowElevation, RoundedCornerShape(50))
//            .clip(RoundedCornerShape(50))
//            .background(containerColor)
//            .clickable { onClick() },
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = text,
//            style = MaterialTheme.typography.bodyMedium,
//            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
//            color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
//        )
//    }
//}

@Composable
fun CategoryDialog(
    category: Category?, //  null - add, exist - edit
    initialType: TransactionType,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onSave: (Category) -> Unit,
    onDelete: (Category) -> Unit
) {
    var name by remember { mutableStateOf(category?.name ?: "") }
    //var selectedColor by remember { mutableStateOf(Color(category?.colorHex?.toInt() ?: CategoryDefaults.presetColors.first().toArgb())) }
    var selectedColor by remember {
        mutableStateOf(Color(category?.colorHex?.toInt() ?: CategoryDefaults.presetColors.first().toArgb()))
    }

    var selectedIcon by remember { mutableStateOf(category?.iconName ?: CategoryDefaults.presetIcons.first()) }

    val isValid = name.isNotBlank()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (category == null) "New Category" else "Edit a Category",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Color", style = MaterialTheme.typography.titleSmall, modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(CategoryDefaults.presetColors) { color ->
                        ColorSelectorItem(
                            color = color,
                            isSelected = color == selectedColor,
                            onClick = { selectedColor = color }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Icon", style = MaterialTheme.typography.titleSmall, modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(150.dp)) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 48.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(CategoryDefaults.presetIcons) { iconName ->
                            IconSelectorItem(
                                iconName = iconName,
                                color = selectedColor,
                                isSelected = iconName == selectedIcon,
                                onClick = { selectedIcon = iconName }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    if (category != null) {
                        IconButton(
                            onClick = { onDelete(category) },
                            modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val newCategory = Category(
                                id = category?.id ?: 0,
                                name = name,
                                iconName = selectedIcon,
                                colorHex = selectedColor.toArgb().toLong(),
                                type = category?.type ?: initialType
                            )
                            onSave(newCategory)
                        },
                        enabled = isValid && !isLoading,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = selectedColor)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}


@Composable
fun ColorSelectorItem(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick() }
            .then(
                if (isSelected) Modifier.border(
                    2.dp,
                    MaterialTheme.colorScheme.onSurface,
                    CircleShape
                ) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
fun IconSelectorItem(
    iconName: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) color.copy(alpha = 0.2f) else Color.LightGray.copy(alpha = 0.2f))
            .border(2.dp, if (isSelected) color else Color.Transparent, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = getIconByName(iconName),
            contentDescription = null,
            tint = if (isSelected) color else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}




//===========================
// ======== Preview ========
//===========================

val previewCategories = listOf(
    Category(
        id = 1, name = "Продукти", iconName = "ic_food",
        colorHex = 0xFF4CAF50, type = TransactionType.EXPENSE
    ),
    Category(
        id = 2, name = "Зарплата", iconName = "ic_salary",
        colorHex = 0xFFFFC107, type = TransactionType.INCOME
    ),
    Category(
        id = 3, name = "Таксі", iconName = "ic_transport",
        colorHex = 0xFF2196F3, type = TransactionType.EXPENSE
    ),
    Category(
        id = 4, name = "Кіно", iconName = "ic_entertainment",
        colorHex = 0xFF9C27B0, type = TransactionType.EXPENSE
    )
)

@Preview(showBackground = true, name = "Category Item")
@Composable
fun CategoryItemPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            CategoryManageItem(
                category = previewCategories[0],
                onClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
            CategoryManageItem(
                category = previewCategories[1],
                onClick = {}
            )
        }
    }
}

@Preview(name = "Edit Dialog")
@Composable
fun EditDialogPreview() {
    MaterialTheme {
        CategoryDialog(
            category = previewCategories[0],
            initialType = TransactionType.EXPENSE,
            onDismiss = {},
            onSave = {},
            onDelete = {},
            isLoading = false
        )
    }
}

@Preview(name = "New Category Dialog")
@Composable
fun NewDialogPreview() {
    MaterialTheme {
        CategoryDialog(
            category = null,
            initialType = TransactionType.EXPENSE,
            onDismiss = {},
            onSave = {},
            onDelete = {},
            isLoading = false
        )
    }
}

@Preview(showBackground = true, name = "Full Screen Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Full Screen Dark")
@Composable
fun CategoryManageScreenPreview() {
    MaterialTheme {
//        CategoryManageContent(
//            categories = previewCategories,
//            onAddCategory = {},
//            onUpdateCategory = {},
//            onDeleteCategory = {},
//            isLoading = false,
//            showDialog = false,
//            categoryToEdit = null,
//            onOpenDialog = { },
//            onCloseDialog = { },
//            snackbarHostState = ,
//        )
    }
}