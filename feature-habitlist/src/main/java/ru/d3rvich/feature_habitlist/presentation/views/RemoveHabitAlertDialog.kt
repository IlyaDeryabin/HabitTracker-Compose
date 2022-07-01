package ru.d3rvich.feature_habitlist.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.d3rvich.feature_habitlist.R

/**
 * Created by Ilya Deryabin at 20.06.2022
 *
 * Виджет диалога удаления привычки.
 */
@Composable
internal fun RemoveHabitAlertDialog(isVisible: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = stringResource(id = R.string.delete_habit))
            },
            text = {
                Text(text = stringResource(id = R.string.remove_message))
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(id = R.string.cancel))
                }
            },
            shape = RoundedCornerShape(size = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun RemoveHabitAlertDialogPreview() {
    Box(Modifier.fillMaxSize()) {
        RemoveHabitAlertDialog(isVisible = true, onConfirm = {}, onDismiss = {})
    }
}