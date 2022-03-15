package ru.d3rvich.habittracker_compose.ui.screens.habit_editor.views

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.SeekBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import ru.d3rvich.habittracker_compose.R
import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.ui.utils.HSVGradient
import ru.d3rvich.habittracker_compose.ui.utils.clearFocusOnClick
import ru.d3rvich.habittracker_compose.ui.utils.clearFocusOnKeyboardDismiss
import ru.d3rvich.habittracker_compose.ui.utils.rememberHSVGradient

@Composable
fun HabitEditorViewEditor(
    modifier: Modifier = Modifier,
    habit: HabitEntity? = null,
    onSaveButtonClicked: (HabitEntity) -> Unit
) {
    var title by rememberSaveable {
        mutableStateOf(habit?.title ?: "")
    }
    var description by rememberSaveable {
        mutableStateOf(habit?.description ?: "")
    }

    var color by rememberSaveable {
        mutableStateOf(Color(0).toArgb())
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .clearFocusOnClick()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                InputTextField(labelResId = R.string.title, value = title) {
                    title = it
                }
            }
            item {
                InputTextField(labelResId = R.string.description, value = description) {
                    description = it
                }
            }
            item {
                ColorPickerBlock {
                    color = it
                }
            }
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        Button(
            onClick = { /*onSaveButtonClicked*/ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Composable
private fun InputTextField(
    modifier: Modifier = Modifier,
    labelResId: Int,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = labelResId),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clearFocusOnKeyboardDismiss(),
            value = value,
            onValueChange = onValueChange
        )
    }
}

@Composable
fun ColorPickerBlock(modifier: Modifier = Modifier, onColorChange: (Int) -> Unit) {
    var colorIndex: Int by rememberSaveable {
        mutableStateOf(180)
    }
    val gradient = rememberHSVGradient()
    var color by rememberSaveable {
        mutableStateOf(gradient.getColorAt(colorIndex))
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorPicker(gradient = gradient) { newColorIndex ->
            colorIndex = newColorIndex
            color = gradient.getColorAt(colorIndex)
            onColorChange(color)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val rgb = gradient.getRGBColorAt(colorIndex)
            Text(text = "RGB = ${rgb.joinToString()}")
            val hsv = gradient.getHSVColorAt(colorIndex).map { String.format("%.2f", it) }
            Text(text = "HSV = ${hsv.joinToString()}")
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
fun ColorPicker(
    modifier: Modifier = Modifier,
    gradient: HSVGradient,
    onColorIndexChange: (Int) -> Unit
) {
    AndroidView(factory = { context ->
        SeekBar(context).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            progressDrawable = gradient
            setPadding(0)
            thumb = ContextCompat.getDrawable(context, R.drawable.color_picker_thumb)
            max = 360
            progress = 180
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
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
    HabitEditorViewEditor {
    }
}
