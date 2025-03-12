package com.mrapps.presentation.exercise_list

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mrapps.mrfit.feature.exercise.presentation.R
import com.mrapps.presentation.preview.ExercisePreviewObjects
import com.mrapps.presentation.preview.ThemePreview
import com.mrapps.presentation.theme.ThemeWithSurface
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ExerciseListScreen(
    navigateToAddExercise: () -> Unit,
    navigateToEditExercise: (exerciseId: String) -> Unit,
    viewModel: ExerciseListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val onAction: (action: ExerciseListAction) -> Unit = { action ->
        when (action) {
            ExerciseListAction.NavigateToAddExercise -> {
                navigateToAddExercise()
            }

            is ExerciseListAction.NavigateToEditExercise -> {
                navigateToEditExercise(action.exerciseId)
            }

            else -> viewModel.onAction(action)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ExerciseListEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }
    }

    ExerciseListContent(
        state = state,
        snackbarState = snackbarHostState,
        onAction = onAction
    )
}

@Composable
fun ExerciseListContent(
    state: ExerciseListState,
    snackbarState: SnackbarHostState,
    onAction: (ExerciseListAction) -> Unit,
) {
    Scaffold(
        topBar = {
            ExerciseListTopAppBar()
        },
        snackbarHost = { SnackbarHost(snackbarState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction.invoke(ExerciseListAction.NavigateToAddExercise)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.button_add)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPaddings ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPaddings)
                .padding(horizontal = 16.dp)
        ) {
            items(state.exercises) { exercise ->
                ExerciseListItem(
                    exercise = exercise,
                    onClick = { id ->
                        onAction.invoke(ExerciseListAction.NavigateToEditExercise(id))
                    }
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListTopAppBar() {
    TopAppBar(
        title = {
            Text(stringResource(R.string.top_bar_title_exercise_list))
        }
    )
}

@ThemePreview
@Composable
fun ExerciseListContentPreview(modifier: Modifier = Modifier) {
    ThemeWithSurface {
        ExerciseListContent(
            state = ExerciseListState(
                exercises = listOf(ExercisePreviewObjects.exercise)
            ),
            snackbarState = SnackbarHostState(),
            onAction = {}
        )
    }
}