package ru.d3rvich.feature_habiteditor

import androidx.navigation.NavController
import ru.d3rvich.feature_habiteditor_api.HabitEditorFeatureApi

/**
 * Created by Ilya Deryabin at 05.07.2022
 */
class HabitEditorFeatureImpl : HabitEditorFeatureApi {
    override fun showScreen(navController: NavController, habitId: String?) {
        if (habitId == null) {
            navController.navigate("habit_creator")
        } else {
            navController.navigate("habit_editor/$habitId")
        }
    }
}