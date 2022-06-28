package ru.d3rvich.feature_habitlist.presentation.model

import ru.d3rvich.core.base.UiState
import ru.d3rvich.feature_habitlist.domain.entities.HabitEntity

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
internal data class HabitListViewState(
    val habitList: List<HabitEntity>?,
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val filterConfig: FilterConfig = FilterConfig.Empty,
) : UiState