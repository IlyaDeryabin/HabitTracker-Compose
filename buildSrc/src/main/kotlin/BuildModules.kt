/**
 * Created by Ilya Deryabin at 01.07.2022
 *
 * Конфигурации build модулей
 */
object BuildModules {
    const val APP = ":app"
    const val CORE = ":core"
    const val API = ":api"

    object Features {
        const val HABIT_LIST_API = ":feature-habitlist-api"
        const val HABIT_LIST = ":feature-habitlist"
        const val HABIT_EDITOR_API = ":feature-habiteditor-api"
        const val HABIT_EDITOR = ":feature-habiteditor"
    }
}