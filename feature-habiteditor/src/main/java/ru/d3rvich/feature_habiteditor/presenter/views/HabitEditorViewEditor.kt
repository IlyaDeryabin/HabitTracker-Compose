package ru.d3rvich.feature_habiteditor.presenter.views

import android.view.ViewGroup
import android.widget.SeekBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import ru.d3rvich.feature_habiteditor.domain.entities.BaseHabitEntity
import ru.d3rvich.feature_habiteditor.domain.entities.HabitEntity
import ru.d3rvich.feature_habiteditor.domain.entities.HabitType
import ru.d3rvich.feature_habiteditor.R
import ru.d3rvich.feature_habiteditor.presenter.utils.HSVGradient
import ru.d3rvich.feature_habiteditor.presenter.utils.rememberHSVGradient

/**
 * Created by Ilya Deryabin at 29.06.2022
 */
@Composable
internal fun HabitEditorViewEditor(
    modifier: Modifier = Modifier,
    habit: HabitEntity? = null,
    isUploading: Boolean,
    onSaveButtonClicked: (BaseHabitEntity) -> Unit,
) {
    var title by rememberSaveable {
        mutableStateOf(habit?.title ?: "")
    }
    var description by rememberSaveable {
        mutableStateOf(habit?.description ?: "")
    }
    var habitPriority: Int by rememberSaveable {
        mutableStateOf(habit?.priority ?: 0)
    }
    var habitType: HabitType? by rememberSaveable {
        mutableStateOf(habit?.type)
    }
    var habitCount by rememberSaveable {
        mutableStateOf(habit?.count?.toString())
    }
    var habitFrequency by rememberSaveable {
        mutableStateOf(habit?.frequency?.toString())
    }
    var color: Int? by rememberSaveable {
        mutableStateOf(habit?.color)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InputTextField(labelResId = R.string.title, value = title) {
                title = it
            }
            InputTextField(labelResId = R.string.description, value = description) {
                description = it
            }
            PrioritySelector(initialPriority = habitPriority,
                onPrioritySelect = { habitPriority = it })
            TypeSelector(initialType = habitType,
                modifier = Modifier.padding(horizontal = 8.dp)) {
                habitType = it
            }
            FrequencyField(modifier = Modifier.padding(horizontal = 12.dp),
                initialCount = habitCount,
                initialFrequency = habitFrequency,
                onCountChange = { habitCount = it },
                onFrequencyChange = { habitFrequency = it })
            ColorPickerBlock(initialColor = color) {
                color = it
            }
            Spacer(modifier = Modifier.height(44.dp))
        }
        Button(
            onClick = {
                val baseHabit = BaseHabitEntity(title = title,
                    description = description,
                    type = habitType,
                    count = habitCount?.toIntOrNull(),
                    frequency = habitFrequency?.toIntOrNull(),
                    priority = habitPriority,
                    color = color)
                onSaveButtonClicked(baseHabit)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            enabled = !isUploading
        ) {
            Text(text = stringResource(id = R.string.save_habit))
        }
    }
}

@Composable
private fun FrequencyField(
    modifier: Modifier = Modifier,
    initialCount: String? = null,
    initialFrequency: String? = null,
    onCountChange: (String) -> Unit,
    onFrequencyChange: (String) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        val focusManager = LocalFocusManager.current
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.amount),
                fontSize = 20.sp)
            var count: String by rememberSaveable {
                mutableStateOf(initialCount ?: "")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(value = count,
                    onValueChange = {
                        count = it
                        onCountChange(count)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    singleLine = true,
                    modifier = Modifier.width(60.dp),
                    textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(id = R.string.times))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.frequency),
                fontSize = 20.sp)
            var frequency: String by rememberSaveable {
                mutableStateOf(initialFrequency ?: "")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(value = frequency,
                    onValueChange = {
                        frequency = it
                        onFrequencyChange(frequency)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    singleLine = true,
                    modifier = Modifier.width(60.dp),
                    textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(id = R.string.days))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PrioritySelector(initialPriority: Int? = null, onPrioritySelect: (Int) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Text(text = "Habit priority",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp, start = 4.dp))
        var expanded by remember {
            mutableStateOf(false)
        }
        val priorities = arrayOf(R.string.high, R.string.medium, R.string.low)
            .map { stringResource(id = it) }
        var value by rememberSaveable {
            mutableStateOf(initialPriority ?: 0)
        }
        ExposedDropdownMenuBox(expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(value = priorities[value],
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.fillMaxWidth())
            ExposedDropdownMenu(expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }) {
                priorities.forEachIndexed { index, priority ->
                    DropdownMenuItem(onClick = {
                        value = index
                        onPrioritySelect(value)
                        expanded = false
                    }) {
                        Text(text = priority)
                    }
                }
            }
        }
    }
}

@Composable
private fun TypeSelector(
    modifier: Modifier = Modifier,
    initialType: HabitType? = null,
    onHabitSelected: (HabitType) -> Unit,
) {
    var habitType: HabitType? by rememberSaveable {
        mutableStateOf(initialType)
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.habit_type),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                val interactionSource = remember {
                    MutableInteractionSource()
                }
                val onClick: () -> Unit = {
                    habitType = HabitType.Good
                    onHabitSelected(habitType!!)
                }
                RadioButton(selected = habitType == HabitType.Good,
                    onClick = onClick,
                    interactionSource = interactionSource)
                Text(text = "Good",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(interactionSource, null, onClick = onClick))
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                val onClick: () -> Unit = {
                    habitType = HabitType.Bad
                    onHabitSelected(habitType!!)
                }
                val interactionSource = remember {
                    MutableInteractionSource()
                }
                RadioButton(selected = habitType == HabitType.Bad,
                    onClick = onClick,
                    interactionSource = interactionSource)
                Text(text = "Bad",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(interactionSource, null, onClick = onClick))
            }
        }
    }
}

@Composable
private fun InputTextField(
    modifier: Modifier = Modifier,
    labelResId: Int,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = labelResId),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
    }
}

@Composable
private fun ColorPickerBlock(
    modifier: Modifier = Modifier,
    initialColor: Int? = null,
    onColorChange: (Int) -> Unit,
) {
    val gradient = rememberHSVGradient()
    var colorIndex: Int by rememberSaveable {
        mutableStateOf(if (initialColor != null) gradient.findHuePositionByColor(color = initialColor) else 180)
    }
    var color by rememberSaveable {
        mutableStateOf(gradient.getColorAt(colorIndex))
    }
    onColorChange(color)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorPicker(gradient = gradient, initialColorIndex = colorIndex) { newColorIndex ->
            colorIndex = newColorIndex
            color = gradient.getColorAt(colorIndex)
            onColorChange(color)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val rgb = gradient.getRGBColorAt(colorIndex)
            Text(text = "RGB = ${rgb.joinToString()}", style = MaterialTheme.typography.body2)
            val hsv = gradient.getHSVColorAt(colorIndex).map { String.format("%.2f", it) }
            Text(text = "HSV = ${hsv.joinToString()}", style = MaterialTheme.typography.body2)
        }
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(color))
        )
    }
}

@Composable
private fun ColorPicker(
    modifier: Modifier = Modifier,
    gradient: HSVGradient,
    initialColorIndex: Int = 180,
    onColorIndexChange: (Int) -> Unit,
) {
    var colorProgress by rememberSaveable {
        mutableStateOf(initialColorIndex)
    }
    AndroidView(factory = { context ->
        SeekBar(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            progressDrawable = gradient
            setPadding(0)
            thumb = ContextCompat.getDrawable(context, R.drawable.color_picker_thumb)
            max = 360
            progress = colorProgress
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    colorProgress = progress
                    onColorIndexChange(progress)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            })
        }
    }, modifier = modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
private fun HabitEditorViewEditorPreview() {
    HabitEditorViewEditor(isUploading = false) {
    }
}