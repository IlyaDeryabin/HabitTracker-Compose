package ru.d3rvich.habittracker_compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.d3rvich.feature_habiteditor.deps.HabitEditorDepsStore
import ru.d3rvich.feature_habiteditor.deps.HabitEditorNavRouter
import ru.d3rvich.feature_habiteditor.presenter.HabitEditorFragment
import ru.d3rvich.feature_habitlist.deps.HabitListDepsStore
import ru.d3rvich.feature_habitlist.deps.HabitListNavRouter
import ru.d3rvich.feature_habitlist.presentation.HabitListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        provideRouters()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HabitListFragment())
            .commit()
    }

    private fun provideRouters() {
        HabitListDepsStore.navRouter = HabitListNavRouterImpl()
        HabitEditorDepsStore.navRouter = HabitEditorNavRouterImpl()
    }

    inner class HabitListNavRouterImpl : HabitListNavRouter {
        override fun navigateToHabitCreator() {
            showFragment(HabitEditorFragment.newInstance())
        }

        override fun navigateToHabitEditorBy(id: String) {
            showFragment(HabitEditorFragment.newInstance(habitId = id))
        }

        private fun showFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    inner class HabitEditorNavRouterImpl : HabitEditorNavRouter {
        override fun popBack() {
            supportFragmentManager.popBackStack()
        }
    }
}