package ru.d3rvich.habittracker_compose.entity

import java.util.*

data class HabitEntity(
    val id: String,
    val title: String,
    val description: String,
    val type: HabitType,
    val count: Int,
    val frequency: Int,
    val priority: Int,
    val color: Int,
    val date: Long,
    val doneDates: List<Long>,
)

data class BaseHabitEntity(
    val title: String,
    val description: String,
    val type: HabitType?,
    val count: Int?,
    val frequency: Int?,
    val priority: Int,
    val color: Int?,
)

fun BaseHabitEntity.toHabitEntity(
    id: String = UUID.randomUUID().toString(),
    date: Long = System.currentTimeMillis(),
    doneDates: List<Long> = emptyList(),
): HabitEntity =
    HabitEntity(
        id = id,
        title = title,
        description = description,
        type = type!!,
        count = count!!,
        frequency = frequency!!,
        priority = priority,
        color = color!!,
        date = date,
        doneDates = doneDates
    )

enum class HabitType(val code: Int) {
    Good(0),
    Bad(1)
}