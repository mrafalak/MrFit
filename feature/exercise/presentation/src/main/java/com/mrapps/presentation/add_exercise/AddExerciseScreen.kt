package com.mrapps.presentation.add_exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.presentation.add_exercise.endurance.AddEnduranceExerciseScreen
import com.mrapps.presentation.add_exercise.strength.AddStrengthExerciseScreen
import com.mrapps.presentation.add_exercise.strength.AddStrengthExerciseScreenContent
import com.mrapps.presentation.add_exercise.strength.AddStrengthExerciseState
import com.mrapps.presentation.component.ExposedDropdownMenuSample
import com.mrapps.mrfit.core.presentation.R as CoreR
import com.mrapps.mrfit.feature.exercise.presentation.R as ExerciseR
import com.mrapps.presentation.preview.ThemePreview
import com.mrapps.presentation.theme.ThemeWithSurface
import com.mrapps.presentation.util.asUiText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddExerciseScreen(
    viewModel: AddExerciseViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val onAction: (action: AddExerciseAction) -> Unit = { action ->
        when (action) {
            AddExerciseAction.NavigateBack -> navigateBack()
            else -> viewModel.onAction(action)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { effect ->
            when (effect) {
                AddExerciseEvent.OnSuccess -> navigateBack()
                is AddExerciseEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message.asString(context))
                }
            }
        }
    }

    AddExerciseContent(
        state = state,
        snackbarState = snackbarHostState,
        onAction = onAction
    ) {
        when (state.form.type) {
            ExerciseTypeEnum.STRENGTH -> {
                AddStrengthExerciseScreen(
                    onFormChange = { typeForm ->
                        onAction.invoke(AddExerciseAction.OnTypeFormChange(typeForm))
                    }
                )
            }

            ExerciseTypeEnum.ENDURANCE -> {
                AddEnduranceExerciseScreen(
                    onFormChange = { typeForm ->
                        onAction.invoke(AddExerciseAction.OnTypeFormChange(typeForm))
                    },
                )
            }
        }
    }
}

@Composable
private fun AddExerciseContent(
    state: AddExerciseState,
    snackbarState: SnackbarHostState,
    onAction: (AddExerciseAction) -> Unit,
    exerciseTypeContent: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            AddExerciseTopAppBar(
                navigateBack = {
                    onAction(AddExerciseAction.NavigateBack)
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction.invoke(AddExerciseAction.SaveNewExercise)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(ExerciseR.string.save)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .padding(innerPaddings)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            NameTextField(
                name = state.form.name,
                onNameChange = {
                    onAction.invoke(AddExerciseAction.OnNameChange(it))
                }
            )
            Spacer(Modifier.height(8.dp))
            DescriptionTextField(
                description = state.form.description,
                onDescriptionChange = {
                    onAction.invoke(AddExerciseAction.OnDescriptionChange(it))
                }
            )
            Spacer(Modifier.height(8.dp))
            ExerciseTypeDropdownMenu(
                selectedOption = state.form.type,
                onOptionSelected = { type ->
                    onAction.invoke(AddExerciseAction.OnTypeChange(type))
                }
            )
            Spacer(Modifier.height(8.dp))

            exerciseTypeContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseTopAppBar(
    navigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(stringResource(ExerciseR.string.top_bar_title_new_exercise))
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(CoreR.string.navigate_back_ic)
                )
            }
        }
    )
}

@Composable
private fun NameTextField(
    modifier: Modifier = Modifier,
    name: String,
    onNameChange: (String) -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = name,
        onValueChange = onNameChange,
        label = {
            Text(stringResource(ExerciseR.string.label_name))
        }
    )
}

@Composable
private fun DescriptionTextField(
    modifier: Modifier = Modifier,
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = description,
        onValueChange = onDescriptionChange,
        label = {
            Text(stringResource(ExerciseR.string.label_description))
        }
    )
}

@Composable
private fun ExerciseTypeDropdownMenu(
    modifier: Modifier = Modifier,
    selectedOption: ExerciseTypeEnum,
    onOptionSelected: (ExerciseTypeEnum) -> Unit
) {
    val context = LocalContext.current

    ExposedDropdownMenuSample(
        modifier = modifier,
        options = ExerciseTypeEnum.entries,
        label = stringResource(ExerciseR.string.label_exercise_type),
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        optionToString = {
            it.asUiText().asString(context)
        }
    )
}

@ThemePreview
@Composable
fun AddExerciseContentPreview(modifier: Modifier = Modifier) {
    ThemeWithSurface {
        AddExerciseContent(
            state = AddExerciseState(),
            snackbarState = SnackbarHostState(),
            onAction = {}
        ) {
            AddStrengthExerciseScreenContent(
                state = AddStrengthExerciseState(),
                onAction = {}
            )
        }
    }
}