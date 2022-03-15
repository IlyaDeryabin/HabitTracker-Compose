package ru.d3rvich.habittracker_compose.ui.screens.habit_editor.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.d3rvich.habittracker_compose.R

@Composable
fun HabitEditorViewError(
    modifier: Modifier = Modifier,
    errorTextResId: Int,
    onReloadButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = errorTextResId))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onReloadButtonClicked) {
            Text(text = stringResource(id = R.string.try_again))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HabitEditorViewErrorPreview() {
    HabitEditorViewError(errorTextResId = R.string.error_message) {
    }
}