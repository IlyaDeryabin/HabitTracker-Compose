package ru.d3rvich.habittracker_compose.ui.screens.habit_list.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.entity.HabitType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitListItem(
    modifier: Modifier = Modifier,
    habit: HabitEntity,
    onHabitClicked: (String) -> Unit,
    onHabitLongClicked: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(92.dp),
        shape = RoundedCornerShape(24.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onLongClick = {
                onHabitLongClicked(habit.id)
            }) {
                onHabitClicked(habit.id)
            }) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(32.dp)
                .background(Brush.horizontalGradient(0.0f to Color(habit.color),
                    1f to Color.Transparent)))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = habit.priority.toString(),
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = habit.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f, true)
                            .padding(start = 8.dp, end = 8.dp)
                            .alignByBaseline(),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h6
                    )
                    val color = when (habit.type) {
                        HabitType.Good -> Color.Green
                        HabitType.Bad -> Color.Red
                    }
                    Canvas(modifier = Modifier.size(20.dp)) {
                        drawCircle(Brush.radialGradient(listOf(color, Color.Transparent)))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = habit.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f, true)
                            .padding(end = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitListItemPreview() {
    val habitEntity = HabitEntity(
        id = "",
        title = "Title",
        description = "Description",
        type = HabitType.Good,
        count = 0,
        frequency = 0,
        priority = 0,
        color = (0xFFFF0000).toInt(),
        date = 0L,
        doneDates = emptyList()
    )
    HabitListItem(habit = habitEntity, onHabitLongClicked = {}, onHabitClicked = {})
}

@Preview(showBackground = true)
@Composable
fun HabitListItemPreviewWithLongTitle() {
    val habitEntity = HabitEntity(
        id = "",
        title = "VeryVeryVeryVeryVeryVeryVeryVeryVeryVeryLongText",
        description = "AlsoVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryLongText",
        type = HabitType.Good,
        count = 0,
        frequency = 0,
        priority = 0,
        color = (0xFFFF0000).toInt(),
        date = 0L,
        doneDates = emptyList()
    )
    HabitListItem(habit = habitEntity, onHabitLongClicked = {}, onHabitClicked = {})
}