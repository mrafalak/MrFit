package com.mrapps.presentation.manage_exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.mrapps.domain.validator.ExerciseValidator
import com.mrapps.presentation.UiText
import com.mrapps.presentation.manage_exercise.exercise_type.ExerciseTypeAction
import com.mrapps.presentation.manage_exercise.exercise_type.ExerciseTypeScreen
import com.mrapps.presentation.manage_exercise.exercise_type.ExerciseTypeViewModel
import com.mrapps.presentation.component.CommonTextField
import com.mrapps.presentation.component.CommonExposedDropdownMenu
import com.mrapps.mrfit.core.presentation.R as CoreR
import com.mrapps.mrfit.feature.exercise.presentation.R as ExerciseR
import com.mrapps.presentation.preview.ThemePreview
import com.mrapps.presentation.theme.ThemeWithSurface
import com.mrapps.presentation.util.asUiText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ManageExerciseScreen(
    exerciseId: String? = null,
    viewModel: ManageExerciseViewModel = hiltViewModel(),
    sharedViewModel: ExerciseTypeViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val typeState by sharedViewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val onAction: (action: ManageExerciseAction) -> Unit = { action ->
        when (action) {
            ManageExerciseAction.NavigateBack -> navigateBack()
            ManageExerciseAction.ValidateForm -> {
                sharedViewModel.onAction(ExerciseTypeAction.ValidateTypeExerciseType)
                viewModel.onAction(ManageExerciseAction.ValidateForm)
            }

            else -> viewModel.onAction(action)
        }
    }

    LaunchedEffect(typeState) {
        onAction.invoke(
            ManageExerciseAction.OnTypeFormChange(
                typeForm = typeState.form,
                isTypeFormValidated = typeState.isTypeFormValidated
            )
        )
    }

    LaunchedEffect(Unit) {
        exerciseId?.let {
            viewModel.onAction(ManageExerciseAction.GetExerciseData(exerciseId))
        }

        viewModel.event.collectLatest { event ->
            when (event) {
                is ManageExerciseEvent.SetInitialTypeForm -> {
                    sharedViewModel.onAction(ExerciseTypeAction.SetInitialTypeForm(event.type))
                }

                ManageExerciseEvent.OnSuccess -> navigateBack()
                is ManageExerciseEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }
    }

    ManageExerciseContent(
        edit = exerciseId != null,
        state = state,
        snackbarState = snackbarHostState,
        onAction = onAction
    ) {
        ExerciseTypeScreen(
            type = state.form.type,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
private fun ManageExerciseContent(
    edit: Boolean,
    state: ManageExerciseState,
    snackbarState: SnackbarHostState,
    onAction: (ManageExerciseAction) -> Unit,
    exerciseTypeContent: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            ManageExerciseTopAppBar(
                edit = edit,
                navigateBack = {
                    onAction(ManageExerciseAction.NavigateBack)
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction.invoke(ManageExerciseAction.ValidateForm)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(ExerciseR.string.button_save)
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
            Text(
                text = stringResource(ExerciseR.string.label_general_data),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(Modifier.height(8.dp))
            NameTextField(
                name = state.form.name,
                error = state.form.nameError,
                onNameChange = {
                    onAction.invoke(ManageExerciseAction.OnNameChange(it))
                }
            )
            Spacer(Modifier.height(8.dp))
            DescriptionTextField(
                description = state.form.description,
                error = state.form.descriptionError,
                onDescriptionChange = {
                    onAction.invoke(ManageExerciseAction.OnDescriptionChange(it))
                }
            )
            Spacer(Modifier.height(8.dp))
            ExerciseTypeDropdownMenu(
                selectedOption = state.form.type,
                onOptionSelected = { type ->
                    onAction.invoke(ManageExerciseAction.OnTypeChange(type))
                }
            )
            Spacer(Modifier.height(32.dp))

            exerciseTypeContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageExerciseTopAppBar(
    edit: Boolean = false,
    navigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            if (edit) {
                Text(stringResource(ExerciseR.string.top_bar_title_edit_exercise))
            } else {
                Text(stringResource(ExerciseR.string.top_bar_title_new_exercise))
            }
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
    error: UiText? = null,
    onNameChange: (String) -> Unit
) {
    CommonTextField(
        modifier = modifier,
        value = name,
        labelResId = ExerciseR.string.label_name,
        error = error,
        onValueChange = { newName ->
            if (newName.length <= ExerciseValidator.MAX_NAME_LENGTH) {
                onNameChange(newName)
            }
        }
    )
}

@Composable
private fun DescriptionTextField(
    modifier: Modifier = Modifier,
    description: String,
    error: UiText? = null,
    onDescriptionChange: (String) -> Unit
) {
    CommonTextField(
        modifier = modifier,
        value = description,
        labelResId = ExerciseR.string.label_description,
        error = error,
        singleLine = false,
        maxLines = 10,
        minLines = 3,
        onValueChange = { newDescription ->
            if (newDescription.length <= ExerciseValidator.MAX_DESCRIPTION_LENGTH) {
                onDescriptionChange(newDescription)
            }
        },
    )
}

@Composable
private fun ExerciseTypeDropdownMenu(
    modifier: Modifier = Modifier,
    selectedOption: ExerciseTypeEnum,
    onOptionSelected: (ExerciseTypeEnum) -> Unit
) {
    val context = LocalContext.current

    CommonExposedDropdownMenu(
        modifier = modifier,
        options = ExerciseTypeEnum.entries,
        labelResId = ExerciseR.string.label_exercise_type,
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        optionToString = {
            it.asUiText().asString(context)
        }
    )
}

@ThemePreview
@Composable
fun ManageExerciseContentPreview(modifier: Modifier = Modifier) {
    ThemeWithSurface {
        ManageExerciseContent(
            edit = false,
            state = ManageExerciseState(),
            snackbarState = SnackbarHostState(),
            onAction = {}
        ) {}
    }
}