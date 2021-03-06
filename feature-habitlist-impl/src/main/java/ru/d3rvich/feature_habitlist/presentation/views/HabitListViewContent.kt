package ru.d3rvich.feature_habitlist.presentation.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.d3rvich.feature_habitlist.R
import ru.d3rvich.feature_habitlist.domain.entities.HabitEntity
import ru.d3rvich.feature_habitlist.domain.entities.HabitType

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun HabitListViewContent(
    isLoading: Boolean,
    habits: List<HabitEntity>?,
    onHabitClicked: (String) -> Unit,
    onHabitLongClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = listOf(R.string.good, R.string.bad).map { stringResource(id = it) }
    val pagerState = rememberPagerState()
    Column(modifier = modifier.fillMaxSize()) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        TabRow(selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
            },
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary) {
            val coroutine = rememberCoroutineScope()
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutine.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = title.uppercase()) })
            }
        }
        HorizontalPager(
            modifier = modifier.fillMaxSize(),
            count = tabs.size,
            state = pagerState
        ) { page ->
            habits?.let {
                when (page) {
                    0 -> {
                        val goodHabits = habits.filter { it.type == HabitType.Good }
                        HabitList(habits = goodHabits,
                            onHabitClicked = onHabitClicked,
                            onHabitLongClicked = onHabitLongClicked)
                    }
                    else -> {
                        val badHabits = habits.filter { it.type == HabitType.Bad }
                        HabitList(habits = badHabits,
                            onHabitClicked = onHabitClicked,
                            onHabitLongClicked = onHabitLongClicked)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HabitList(
    habits: List<HabitEntity>,
    onHabitClicked: (String) -> Unit,
    onHabitLongClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (habits.isEmpty()) {
        Box(modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp),
            contentAlignment = Alignment.TopCenter) {
            Text(text = stringResource(id = R.string.empty_list))
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(habits, key = { it.id }) {
                HabitListItem(habit = it,
                    onHabitClicked = onHabitClicked,
                    onHabitLongClicked = onHabitLongClicked,
                    modifier = Modifier.animateItemPlacement())
            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HabitListViewContentEmptyListPreview() {
    HabitListViewContent(isLoading = false,
        habits = emptyList(),
        onHabitClicked = {},
        onHabitLongClicked = {})
}

@Preview(showBackground = true)
@Composable
private fun HabitListViewContentPreview() {
    val habits: MutableList<HabitEntity> = mutableListOf()
    for (i in 0 until 20) {
        val habit = HabitEntity(
            id = i.toString(),
            title = "Title $i",
            description = "Description",
            type = if (i % 2 == 0) HabitType.Good else HabitType.Bad,
            count = 0,
            frequency = 0,
            priority = 0,
            color = (0xFFFF0000).toInt(),
            date = 0L,
            doneDates = emptyList()
        )
        habits.add(habit)
    }
    HabitListViewContent(habits = habits,
        onHabitClicked = {},
        isLoading = false,
        onHabitLongClicked = {})
}

@Preview(showBackground = true)
@Composable
private fun HabitListViewContentWithDialogPreview() {
    HabitListViewContentPreview()
    RemoveHabitAlertDialog(isVisible = true, onConfirm = {}, onDismiss = {})
}