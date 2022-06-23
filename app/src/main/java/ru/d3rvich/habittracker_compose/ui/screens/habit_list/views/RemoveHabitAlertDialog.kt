package ru.d3rvich.habittracker_compose.ui.screens.habit_list.views

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.d3rvich.habittracker_compose.R

/**
 * Created by Ilya Deryabin at 20.06.2022
 *
 * Виджет диалога удаления привычки.
 */
@Composable
fun RemoveHabitAlertDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
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
        })
}