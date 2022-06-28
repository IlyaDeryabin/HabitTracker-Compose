package ru.d3rvich.feature_habitlist.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.d3rvich.feature_habitlist.R
import ru.d3rvich.feature_habitlist.presentation.HabitListViewModel
import ru.d3rvich.feature_habitlist.presentation.model.HabitListEvent

/**
 * Created by Ilya Deryabin at 16.06.2022
 */
@Composable
internal fun HabitListViewFilter(
    modifier: Modifier = Modifier,
    viewModel: HabitListViewModel,
    peekHeight: Dp = 0.dp,
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
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.LightGray)) {
            val state by viewModel.uiState.collectAsState()
            TextField(value = state.filterConfig.filterText,
                onValueChange = {
                    viewModel.obtainEvent(HabitListEvent.OnFilterTextChange(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HabitListViewFilterPreview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
//        HabitListViewFilter(peekHeight = 52.dp)
    }
}