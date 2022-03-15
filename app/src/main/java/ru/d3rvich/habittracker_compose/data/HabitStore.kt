package ru.d3rvich.habittracker_compose.data

import kotlinx.coroutines.delay
import ru.d3rvich.habittracker_compose.entity.HabitEntity
import ru.d3rvich.habittracker_compose.entity.HabitType

object HabitStore {
    private val habits: MutableList<HabitEntity> = mutableListOf()

    init {
        for (i in 0 until 10) {
            val habit = HabitEntity(
                id = i.toString(),
                title = "Title $i",
                description = "Description",
                type = if (i % 2 == 0) HabitType.Good else HabitType.Bad,
                count = 0,
                frequency = 0,
                priority = 0,
                color = 0xFFFF0000,
                date = 0L,
                doneDates = emptyList()
            )
            habits.add(habit)
        }
    }

    suspend fun getHabits(): List<HabitEntity> {
        delay(500) // Оправдываю suspend
        return habits.toList()
    }

    suspend fun getHabitBy(id: String): HabitEntity {
        delay(1)
        val habit = habits.find { it.id == id }
        checkNotNull(habit) { "Habit not found" }
        return habit
    }

    suspend fun addHabit(habit: HabitEntity): Boolean {
        delay(1)
        return habits.add(habit)
    }

    suspend fun editHabit(habit: HabitEntity) {
        delay(1)
        val index = habits.indexOf(habit)
        check(index > -1) { "Habit not found" }
        habits[index] = habit
    }
}