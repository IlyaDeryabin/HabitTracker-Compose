package ru.d3rvich.feature_habiteditor.data.mappers

import ru.d3rvich.api.dto.HabitDto
import ru.d3rvich.feature_habiteditor.domain.entities.HabitEntity
import ru.d3rvich.feature_habiteditor.domain.entities.HabitType

/**
 * Created by Ilya Deryabin at 29.06.2022
 */
internal fun HabitDto.toHabitEntity(): HabitEntity {
    val habitType = HabitType.values().find { it.code == type }
        ?: error("Неизвествый код типа привычки: $type")
    return HabitEntity(id = id,
        title = title,
        description = description,
        type = habitType,
        count = count,
        frequency = frequency,
        priority = priority,
        color = color,
        date = date,
        doneDates = doneDates)
}

internal fun HabitEntity.toHabitDto(): HabitDto = HabitDto(id = id,
    title = title,
    description = description,
    type = type.code,
    count = count,
    frequency = frequency,
    priority = priority,
    color = color,
    date = date,
    doneDates = doneDates)