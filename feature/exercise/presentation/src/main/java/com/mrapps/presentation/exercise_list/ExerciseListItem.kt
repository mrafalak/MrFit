package com.mrapps.presentation.exercise_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mrapps.domain.model.Exercise
import com.mrapps.presentation.preview.ExercisePreviewObjects
import com.mrapps.presentation.preview.ThemePreview
import com.mrapps.presentation.theme.ThemeWithSurface

@Composable
fun ExerciseListItem(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    onClick: (exerciseId: String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        onClick = {
            onClick(exercise.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = exercise.name, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@ThemePreview
@Composable
fun ExerciseListItemLongNamePreview(modifier: Modifier = Modifier) {
    ThemeWithSurface {
        ExerciseListItem(
            exercise = ExercisePreviewObjects.exercise.copy(name = "Wyciskanie sztangi na ławce płaskiej z nogami na ławce. Wyciskanie sztangi na ławce płaskiej z nogami na ławce."),
            onClick = {}
        )
    }
}

@ThemePreview
@Composable
fun ExerciseListItemShortNamePreview(modifier: Modifier = Modifier) {
    ThemeWithSurface {
        ExerciseListItem(
            exercise = ExercisePreviewObjects.exercise.copy(name = "Wyciskanie sztangi"),
            onClick = {}
        )
    }
}