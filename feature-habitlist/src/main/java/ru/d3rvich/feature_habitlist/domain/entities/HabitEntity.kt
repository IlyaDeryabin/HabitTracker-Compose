package ru.d3rvich.feature_habitlist.domain.entities

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
internal data class HabitEntity(
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

internal enum class HabitType(val code: Int) {
    Good(0),
    Bad(1)
}