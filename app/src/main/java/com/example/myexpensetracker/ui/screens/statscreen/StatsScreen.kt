package com.example.expensetracker.components

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myexpensetracker.data.enums.TransactionType
import com.example.myexpensetracker.ui.components.TypeSelector
import com.example.myexpensetracker.ui.components.TypeSelectorDefaults.onSurfaceBackground
import com.example.myexpensetracker.ui.screens.statscreen.StatsViewModel
import com.example.myexpensetracker.utils.ChartData

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel()
)
{
    val uiState by viewModel.statsUiState.collectAsState()

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        StatsContent(
            donutData = uiState.donutData,
            barData = uiState.barData,
            totalAmount = uiState.totalAmount,
            selectedType = uiState.selectedType,
            onTypeChanged = viewModel::onTypeChanged
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsContent(
    donutData: List<ChartData>,
    barData: List<Pair<String, Float>>,
    totalAmount: Double,
    selectedType: TransactionType,
    onTypeChanged: (TransactionType) -> Unit
) { // MaterialTheme.colorScheme.background
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Statistics", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // 1. Toggle
            item {
//                StatsTypeToggle(
//                    selectedType = selectedType,
//                    onTypeChanged = onTypeChanged
//                )
                TypeSelector(
                    isExpense = selectedType == TransactionType.EXPENSE,
                    onClickExpense = {onTypeChanged(TransactionType.EXPENSE)},
                    onClickIncome = {onTypeChanged(TransactionType.INCOME)},
                    colors = onSurfaceBackground(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
                        .padding(4.dp)
                )
            }

            // 2. Donut Chart
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Breakdown by category", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(24.dp))

                            if (donutData.isEmpty()) {
                                Text("No data", color = Color.Gray)
                            } else {
                                DonutChart(
                                    data = donutData,
                                    totalAmount = totalAmount,
                                    modifier = Modifier.size(220.dp),
                                    thickness = 25.dp
                                )
                            }
                        }
                    }
                }
            }

            // 3. Date Bar Chart
            item {
                Column {
                    Text("Weekly dynamics", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    SimpleBarChart(
                        data = barData,
                        barColor = if (selectedType == TransactionType.EXPENSE) Color(0xFFE53935) else Color(0xFF43A047),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
            }

            // 4. Details
            item {
                Text("Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            items(donutData) { item ->
                StatsCategoryRow(item)
            }
        }
    }
}


@Composable
fun StatsTypeToggle(
    selectedType: TransactionType,
    onTypeChanged: (TransactionType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(4.dp)
    ) {
        ToggleTab(
            text = "Expense",
            isSelected = selectedType == TransactionType.EXPENSE,
            onClick = { onTypeChanged(TransactionType.EXPENSE) },
            activeColor = Color(0xFFE53935),
            modifier = Modifier.weight(1f)
        )

        ToggleTab(
            text = "Income",
            isSelected = selectedType == TransactionType.INCOME,
            onClick = { onTypeChanged(TransactionType.INCOME) },
            activeColor = Color(0xFF43A047),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ToggleTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    activeColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
            contentColor = if (isSelected) activeColor else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = if (isSelected) ButtonDefaults.buttonElevation(2.dp) else ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(text, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
fun StatsCategoryRow(data: ChartData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(data.color, RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = data.label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "${(data.percentage * 100).toInt()}%",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.width(50.dp)
        )

        Text(
            text = "₴${data.value.toInt()}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun DonutChart(
    data: List<ChartData>,
    totalAmount: Double,
    modifier: Modifier = Modifier,
    thickness: Dp = 30.dp
) {
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val chartDiameter = size.minDimension
            val radius = chartDiameter / 2
            val strokeWidth = thickness.toPx()

            drawCircle(
                color = Color.LightGray.copy(alpha = 0.2f),
                radius = radius - strokeWidth / 2,
                style = Stroke(width = strokeWidth)
            )

            var startAngle = -90f

            data.forEach { slice ->
                val sweepAngle = 360f * slice.percentage * animationProgress.value

                drawArc(
                    color = slice.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
                    size = Size(chartDiameter - strokeWidth, chartDiameter - strokeWidth),
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                )

                startAngle += sweepAngle
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Text(
                text = "$${totalAmount}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SimpleBarChart(
    data: List<Pair<String, Float>>,
    barColor: Color,
    modifier: Modifier = Modifier
) {
    val maxValue = data.maxOfOrNull { it.second } ?: 1f

    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animationProgress.animateTo(1f, tween(1000))
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { (label, value) ->
            val barHeightRatio = (value / maxValue) * animationProgress.value

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight(if (value > 0) barHeightRatio else 0.01f)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(if (value > 0) barColor else Color.LightGray.copy(alpha = 0.3f))
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}



//===========================
// ======== Preview ========
//===========================


val sampleDonutData = listOf(
    ChartData("Продукти", 5000f, Color(0xFF4CAF50), 0.5f),      // 50%
    ChartData("Транспорт", 2000f, Color(0xFF2196F3), 0.2f),     // 20%
    ChartData("Розваги", 1500f, Color(0xFFFF9800), 0.15f),      // 15%
    ChartData("Кафе", 1500f, Color(0xFF9C27B0), 0.15f)          // 15%
)

val sampleBarData = listOf(
    "Пн" to 200f,
    "Вт" to 450f,
    "Ср" to 100f,
    "Чт" to 600f,
    "Пт" to 300f,
    "Сб" to 800f,
    "Нд" to 150f
)


@Preview(showBackground = true, name = "Donut Chart")
@Composable
fun DonutChartPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp).size(200.dp)) {
            DonutChart(
                data = sampleDonutData,
                totalAmount = 10000.0
            )
        }
    }
}

@Preview(showBackground = true, name = "Bar Chart")
@Composable
fun BarChartPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp).height(150.dp)) {
            SimpleBarChart(
                data = sampleBarData,
                barColor = Color(0xFFE53935)
            )
        }
    }
}

@Preview(showBackground = true, name = "Type Toggle")
@Composable
fun TogglePreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            StatsTypeToggle(
                selectedType = TransactionType.EXPENSE,
                onTypeChanged = {}
            )
        }
    }
}


@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StatsScreenPreview() {
    MaterialTheme {
        StatsContent(
            donutData = sampleDonutData,
            barData = sampleBarData,
            totalAmount = 10000.0,
            selectedType = TransactionType.EXPENSE,
            onTypeChanged = {},
        )
    }
}

@Preview(name = "Empty State")
@Composable
fun StatsEmptyPreview() {
    MaterialTheme {
        StatsContent(
            donutData = emptyList(),
            barData = emptyList(),
            totalAmount = 0.0,
            selectedType = TransactionType.INCOME,
            onTypeChanged = {},
        )
    }
}