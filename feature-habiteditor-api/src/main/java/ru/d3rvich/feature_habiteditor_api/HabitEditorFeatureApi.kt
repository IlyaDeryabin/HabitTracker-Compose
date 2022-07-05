package ru.d3rvich.feature_habiteditor_api

import androidx.navigation.NavController

/**
 * Created by Ilya Deryabin at 05.07.2022
 */
interface HabitEditorFeatureApi {

    fun showScreen(navController: NavController, habitId: String? = null)
}