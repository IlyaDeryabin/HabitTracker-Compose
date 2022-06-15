package ru.d3rvich.habittracker_compose.ui.screens.habit_list.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import ru.d3rvich.habittracker_compose.R
import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.entity.HabitType

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun HabitListViewContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    habits: List<HabitEntity>?,
    onHabitClicked: (String) -> Unit,
    onHabitLongClicked: (String) -> Unit,
) {
    val tabs = listOf(R.string.good, R.string.bad).map { stringResource(id = it) }
    val pagerState = rememberPagerState()
    Column(modifier = modifier.fillMaxSize()) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        TabRow(selectedTabIndex = pagerState.currentPage, indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }) {
            val coroutine = rememberCoroutineScope()
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutine.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = title) })
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

@ExperimentalFoundationApi
@Composable
private fun HabitList(
    modifier: Modifier = Modifier,
    habits: List<HabitEntity>,
    onHabitClicked: (String) -> Unit,
    onHabitLongClicked: (String) -> Unit,
) {
    if (habits.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Список пуст")
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(habits) {
                HabitListItem(habit = it,
                    onHabitClicked = onHabitClicked,
                    onHabitLongClicked = onHabitLongClicked)
            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@ExperimentalPagerApi
@ExperimentalFoundationApi
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
    HabitListViewContent(habits = habits, onHabitClicked = {}, isLoading = false) {}
}