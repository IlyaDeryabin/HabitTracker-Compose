package ru.d3rvich.core.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import ru.d3rvich.core.theme.model.ThemeMode

/**
 * Created by Ilya Deryabin at 15.07.2022
 *
 * Холдер темы приложения
 */
object ThemeConfig {
    private val mCurrentThemeMode = mutableStateOf(ThemeMode.DEFAULT)
    val currentThemeMode: State<ThemeMode>
        get() = mCurrentThemeMode

    fun changeTheme(newThemeMode: ThemeMode) {
        mCurrentThemeMode.value = newThemeMode
    }
}