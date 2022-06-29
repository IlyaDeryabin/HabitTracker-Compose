package ru.d3rvich.core.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager

/**
 * Created by Ilya Deryabin at 29.06.2022
 *
 * [Modifier] позволяющий сбрасывать фокус при нажатии на пустое поле.
 */
fun Modifier.clearFocusOnClick(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    return@composed this.clickable(MutableInteractionSource(), indication = null) {
        focusManager.clearFocus()
    }
}

/**
 * Created by Ilya Deryabin at 29.06.2022
 *
 * [Modifier] позволяющий сбрасывать фокус при сворачивании системной клавиатуры.
 */
@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    var isFocused by remember {
        mutableStateOf(false)
    }
    var keyboardAppearedSinceLastFocused by remember {
        mutableStateOf(false)
    }
    if (isFocused) {
        val imeIsVisible = WindowInsets.Companion.isImeVisible
        val focusManager = LocalFocusManager.current
        LaunchedEffect(imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}