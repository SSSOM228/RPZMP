package com.example.myexpensetracker.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class TypeSelectorColors(
    val containerColor: Color,
    val selectedButtonColor: Color,
    val selectedContentColorExpense: Color,
    val selectedContentColorIncome: Color,
    val unselectedContentColor: Color
)
object TypeSelectorDefaults {

    // Add/Edit
    @Composable
    fun onColoredBackground() = TypeSelectorColors(
        containerColor = Color.White.copy(alpha = 0.2f),
        selectedButtonColor = Color.White,
        selectedContentColorExpense = Color.Black, // Або можна зробити Color(0xFFE53935)
        selectedContentColorIncome = Color.Black,  // Або можна зробити Color(0xFF43A047)
        unselectedContentColor = Color.White.copy(alpha = 0.8f)
    )

    // фільтрів/категорій
    @Composable
    fun onSurfaceBackground() = TypeSelectorColors(
        containerColor = Color.Transparent,
        selectedButtonColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),

        selectedContentColorExpense = Color(0xFFE53935),
        selectedContentColorIncome = Color(0xFF43A047),

        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}



@Composable
fun TypeSelector(
    isExpense: Boolean,
    onClickExpense: () -> Unit,
    onClickIncome: () -> Unit,
    colors: TypeSelectorColors = TypeSelectorDefaults.onSurfaceBackground(),
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TypeButton(
            text = "Expense",
            isSelected = isExpense,
            selectedTextColor = colors.selectedContentColorExpense,
            selectedBackgroundColor = colors.selectedButtonColor,
            unselectedTextColor = colors.unselectedContentColor,
            onClick = onClickExpense,
            modifier = Modifier.weight(1f)
        )

        TypeButton(
            text = "Income",
            isSelected = !isExpense,
            selectedTextColor = colors.selectedContentColorIncome,
            selectedBackgroundColor = colors.selectedButtonColor,
            unselectedTextColor = colors.unselectedContentColor,
            onClick = onClickIncome,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TypeButton(
    text: String,
    isSelected: Boolean,
    selectedTextColor: Color,
    selectedBackgroundColor: Color,
    unselectedTextColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) selectedBackgroundColor else Color.Transparent,
        label = "bgColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) selectedTextColor else unselectedTextColor,
        label = "textColor"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isSelected && selectedBackgroundColor != Color.Transparent) 2.dp else 0.dp,
        label = "shadow"
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            //.shadow(shadowElevation, RoundedCornerShape(50))
            .padding(4.dp)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                //indication = null
            ) { onClick() },

        contentAlignment = Alignment.Center,
        ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}