package com.example.tipcalculator.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val IconButtonSizeModifier = Modifier.padding()
@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClickAction: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
    backgroundColor: Color =  MaterialTheme.colorScheme.background,
    elevation: Dp = 4.dp
) {
    Card(modifier = modifier
        .padding(all = 4.dp)
        .clickable { onClickAction.invoke() }
        .then(IconButtonSizeModifier),
        shape = CircleShape,
        colors = CardDefaults.cardColors(backgroundColor),
        elevation = CardDefaults.cardElevation(elevation)) {
        Icon(imageVector = imageVector, contentDescription = "Plus or minus icon",
            tint = tint
        )
    }

}