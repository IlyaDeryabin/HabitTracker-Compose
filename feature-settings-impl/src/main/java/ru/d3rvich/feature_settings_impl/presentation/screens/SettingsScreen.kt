package ru.d3rvich.feature_settings_impl.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.d3rvich.core.theme.ThemeConfig
import ru.d3rvich.core.theme.model.ThemeMode

/**
 * Created by Ilya Deryabin at 15.07.2022
 *
 * Экран настройки
 */
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToAboutScreen: () -> Unit,
) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(text = "Settings")
                },
                backgroundColor = MaterialTheme.colors.surface,
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back")
                    }
                })
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .align(Alignment.TopCenter)) {
                Text(text = "Theme setting",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp))
                val currentThemeMode = ThemeConfig.currentThemeMode.value
                RadioButton(selected = currentThemeMode == ThemeMode.DEFAULT,
                    onClick = { ThemeConfig.changeTheme(ThemeMode.DEFAULT) },
                    text = "Default")
                RadioButton(selected = currentThemeMode == ThemeMode.LIGHT,
                    onClick = { ThemeConfig.changeTheme(ThemeMode.LIGHT) },
                    text = "Light Theme")
                RadioButton(selected = currentThemeMode == ThemeMode.DARK,
                    onClick = { ThemeConfig.changeTheme(ThemeMode.DARK) },
                    text = "Dark Theme")
            }
            TextButton(onClick = { navigateToAboutScreen() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(4.dp)) {
                Icon(imageVector = Icons.Outlined.Info,
                    contentDescription = "About application",
                    modifier = Modifier.padding(end = 4.dp))
                Text(text = "About application")
            }
        }
    }
}

@Composable
private fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .selectable(selected = selected,
            role = Role.RadioButton,
            onClick = onClick)
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(navigateBack = {}, navigateToAboutScreen = {})
}