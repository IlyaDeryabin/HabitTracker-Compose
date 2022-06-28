package ru.d3rvich.feature_habitlist.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnLifecycleDestroyed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.d3rvich.feature_habitlist.deps.HabitListComponentViewModel
import ru.d3rvich.feature_habitlist.presentation.theme.HabitTrackerComposeTheme
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 24.06.2022
 */
class HabitListFragment : Fragment() {

    @Inject
    internal lateinit var habitListViewModelFactory: HabitListViewModel.Factory

    private val habitListViewModel: HabitListViewModel by viewModels {
        habitListViewModelFactory
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[HabitListComponentViewModel::class.java]
            .habitListComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(DisposeOnLifecycleDestroyed(viewLifecycleOwner))
            setContent {
                HabitTrackerComposeTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .navigationBarsPadding(),
                        color = MaterialTheme.colors.background) {
                        HabitListScreen(viewModel = habitListViewModel)
                    }
                }
            }
        }
    }
}