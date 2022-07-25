package ru.d3rvich.feature_habitlist.presentation.views

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.d3rvich.core.utils.clearFocusOnClick
import ru.d3rvich.feature_habitlist.R
import ru.d3rvich.feature_habitlist.presentation.HabitListViewModel
import ru.d3rvich.feature_habitlist.presentation.model.*
import ru.d3rvich.feature_habitlist.presentation.model.FilterConfig
import ru.d3rvich.feature_habitlist.presentation.model.HabitListEvent
import ru.d3rvich.feature_habitlist.presentation.model.HabitSortingVariants

/**
 * Created by Ilya Deryabin at 16.06.2022
 */
@Composable
internal fun HabitListViewFilter(
    viewModel: HabitListViewModel,
    modifier: Modifier = Modifier,
    peekHeight: Dp = 60.dp,
) {
    HabitListViewFilterContent(modifier = modifier.clearFocusOnClick(),
        filterConfig = viewModel.uiState.collectAsState().value.filterConfig,
        onFilterTextChange = {
            viewModel.obtainEvent(HabitListEvent.OnFilterTextChange(it))
        },
        onSortingEngineChange = {
            viewModel.obtainEvent(HabitListEvent.OnSortingMethodChange(it))
        },
        onSortDirectionChange = {
            viewModel.obtainEvent(HabitListEvent.OnSortDirectionChange(it))
        },
        peekHeight = peekHeight
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HabitListViewFilterContent(
    filterConfig: FilterConfig,
    onFilterTextChange: (String) -> Unit,
    onSortingEngineChange: (HabitSortingEngine) -> Unit,
    onSortDirectionChange: (SortDirection) -> Unit,
    modifier: Modifier = Modifier,
    peekHeight: Dp = 60.dp,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(peekHeight)
            .background(Color.Gray)) {
            Text(text = stringResource(id = R.string.filter_label),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.h5.copy(color = Color.Black))
        }
        Column(modifier = Modifier
            .fillMaxWidth()) {
            val focusManager = LocalFocusManager.current
            OutlinedTextField(value = filterConfig.filterText,
                onValueChange = { onFilterTextChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp),
                singleLine = true,
                trailingIcon = {
                    AnimatedVisibility(visible = filterConfig.filterText.isNotEmpty(),
                        enter = scaleIn(),
                        exit = scaleOut()) {
                        IconButton(onClick = { onFilterTextChange("") }) {
                            Icon(imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear filter")
                        }
                    }
                },
                placeholder = {
                    Text(text = "Filter")
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            SortingSelector(sortingEngine = filterConfig.sortingEngine,
                onSortingEngineChange = onSortingEngineChange,
                sortDirection = filterConfig.sortDirection,
                onSortDirectionChange = onSortDirectionChange,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp, bottom = 12.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SortingSelector(
    sortingEngine: HabitSortingEngine,
    onSortingEngineChange: (HabitSortingEngine) -> Unit,
    sortDirection: SortDirection,
    onSortDirectionChange: (SortDirection) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sortingList = arrayOf(R.string.no_sorting to HabitSortingVariants.None,
        R.string.by_title to HabitSortingVariants.ByTitle,
        R.string.by_priority to HabitSortingVariants.ByPriority,
        R.string.by_creation_time to HabitSortingVariants.ByCreatedTime)
        .map { stringResource(it.first) to it.second }
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        var expanded by rememberSaveable {
            mutableStateOf(false)
        }
        val focusManager = LocalFocusManager.current
        ExposedDropdownMenuBox(expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.weight(1f)) {
            val engineText = sortingList.find { it.second == sortingEngine }
            OutlinedTextField(
                value = engineText?.first ?: "",
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            }) {
                sortingList.forEach { engine ->
                    DropdownMenuItem(onClick = {
                        onSortingEngineChange(engine.second)
                        expanded = false
                        focusManager.clearFocus()
                    }) {
                        Text(text = engine.first)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        SortDirectionToggleButton(current = sortDirection,
            target = SortDirection.ByAscending,
            onCheckedChange = { onSortDirectionChange(SortDirection.ByAscending) })
        SortDirectionToggleButton(current = sortDirection,
            target = SortDirection.ByDescending,
            onCheckedChange = { onSortDirectionChange(SortDirection.ByDescending) })
    }
}

@Composable
private fun SortDirectionToggleButton(
    current: SortDirection,
    target: SortDirection,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tintColor by animateColorAsState(targetValue = if (current == target) {
        MaterialTheme.colors.primary
    } else {
        LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    })
    IconToggleButton(checked = current == target,
        onCheckedChange = {
            onCheckedChange()
        },
        modifier = modifier) {
        Icon(
            imageVector = if (target == SortDirection.ByAscending) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            },
            contentDescription = if (target == SortDirection.ByAscending) {
                "Sort by ascending"
            } else {
                "Sort by descending"
            },
            tint = tintColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HabitListViewFilterPreview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        val filterConfig =
            FilterConfig("Filter Text",
                HabitSortingVariants.ByCreatedTime,
                SortDirection.ByDescending)
        HabitListViewFilterContent(filterConfig = filterConfig,
            onFilterTextChange = {},
            onSortingEngineChange = {},
            onSortDirectionChange = {})
    }
}