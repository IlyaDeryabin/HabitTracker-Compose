package ru.d3rvich.habittracker_compose.ui.screens.habit_list.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HabitListViewError(modifier: Modifier = Modifier, onReloadButtonClicked: () -> Unit) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(text = "Ошибка при загрузке")
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(onClick = onReloadButtonClicked) {
            Text(text = "Reload")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitListViewErrorPreview() {
    HabitListViewError {
    }
}