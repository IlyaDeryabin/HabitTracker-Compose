package ru.d3rvich.habittracker_compose.ui.screens.habit_list.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.entity.HabitType

@ExperimentalMaterialApi
@Composable
fun HabitListItem(
    modifier: Modifier = Modifier,
    habit: HabitEntity,
    onHabitClicked: (String) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        onClick = { onHabitClicked(habit.id) }) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Divider(
                modifier = Modifier
                    .width(32.dp)
                    .height(80.dp),
                color = Color(habit.color)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .padding(start = 8.dp, end = 12.dp)
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
                    val textColor = when (habit.type) {
                        HabitType.Good -> Color.Green
                        HabitType.Bad -> Color.Red
                    }
                    Text(
                        text = habit.type.name,
                        modifier = Modifier.alignByBaseline(),
                        color = textColor
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = habit.description,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f, true)
                            .padding(end = 8.dp),
                        textAlign = TextAlign.Start
                    )
                    Text(text = "${habit.frequency} times")
                }
            }
        }
    }
}

@ExperimentalMaterialApi
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
        color = 0xFFFF0000,
        date = 0L,
        doneDates = emptyList()
    )
    HabitListItem(habit = habitEntity) {}
}

@ExperimentalMaterialApi
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
        color = 0xFFFF0000,
        date = 0L,
        doneDates = emptyList()
    )
    HabitListItem(habit = habitEntity) {}
}