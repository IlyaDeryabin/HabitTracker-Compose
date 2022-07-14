package ru.d3rvich.feature_habitlist.presentation.model

import ru.d3rvich.feature_habitlist.domain.entities.HabitEntity

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
internal data class FilterConfig(
    val filterText: String,
    val sortingEngine: HabitSortingEngine,
    val sortDirection: SortDirection,
) {
    fun execute(habits: List<HabitEntity>): List<HabitEntity> {
        val filteredHabits = habits.filter {
            it.title.contains(filterText, ignoreCase = true)
        }
        return sortingEngine.sort(filteredHabits, sortDirection)
    }

    companion object {
        val Empty = FilterConfig(filterText = "",
            sortingEngine = HabitSortingVariants.None,
            sortDirection = SortDirection.ByAscending)
    }
}

internal interface HabitSortingEngine {
    fun sort(habits: List<HabitEntity>, direction: SortDirection): List<HabitEntity>
}

internal sealed interface HabitSortingVariants : HabitSortingEngine {
    object None : HabitSortingVariants {
        override fun sort(habits: List<HabitEntity>, direction: SortDirection): List<HabitEntity> =
            habits
    }

    object ByTitle : HabitSortingVariants {
        override fun sort(habits: List<HabitEntity>, direction: SortDirection): List<HabitEntity> {
            return when (direction) {
                SortDirection.ByAscending -> habits.sortedBy { it.title }
                SortDirection.ByDescending -> habits.sortedByDescending { it.title }
            }
        }
    }

    object ByPriority : HabitSortingVariants {
        override fun sort(habits: List<HabitEntity>, direction: SortDirection): List<HabitEntity> {
            return when (direction) {
                SortDirection.ByAscending -> habits.sortedBy { it.priority }
                SortDirection.ByDescending -> habits.sortedByDescending { it.priority }
            }
        }
    }

    object ByCreatedTime : HabitSortingVariants {
        override fun sort(habits: List<HabitEntity>, direction: SortDirection): List<HabitEntity> {
            return when (direction) {
                SortDirection.ByAscending -> habits.sortedBy { it.date }
                SortDirection.ByDescending -> habits.sortedByDescending { it.date }
            }
        }
    }
}

enum class SortDirection {
    ByAscending,
    ByDescending
}