package ru.d3rvich.feature_habiteditor.presenter

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
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.d3rvich.core.theme.HabitTrackerComposeTheme
import ru.d3rvich.feature_habiteditor.deps.HabitEditorComponentViewModel
import javax.inject.Inject

/**
 * Created by Ilya Deryabin at 28.06.2022
 */
class HabitEditorFragment : Fragment() {

    @Inject
    internal lateinit var habitEditorViewModelAssistedFactory: HabitEditorViewModel.HabitIdAssistedFactory

    private val habitEditorViewModelFactory: HabitEditorViewModel.Factory by lazy {
        habitEditorViewModelAssistedFactory.create(arguments?.getString(HABIT_ID_KEY, null))
    }

    private val habitEditorViewModel: HabitEditorViewModel by viewModels { habitEditorViewModelFactory }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[HabitEditorComponentViewModel::class.java]
            .habitEditorComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner))
            setContent {
                HabitTrackerComposeTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .navigationBarsPadding(),
                        color = MaterialTheme.colors.background) {
                        HabitEditorScreen(viewModel = habitEditorViewModel)
                    }
                }
            }
        }
    }

    companion object {
        private const val HABIT_ID_KEY = "habitId"
        fun newInstance(habitId: String? = null): HabitEditorFragment {
            return HabitEditorFragment().apply {
                habitId?.let {
                    val bundle = Bundle()
                    bundle.putString(HABIT_ID_KEY, habitId)
                    arguments = bundle
                }
            }
        }
    }
}