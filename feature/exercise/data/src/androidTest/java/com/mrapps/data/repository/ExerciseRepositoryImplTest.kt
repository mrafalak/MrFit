package com.mrapps.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.mrapps.android_testing.MrFitAndroidTest
import com.mrapps.domain.DataError
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.test.exercise
import com.mrapps.test.util.assertResultError
import com.mrapps.test.util.assertResultSuccess
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ExerciseRepositoryImplTest : MrFitAndroidTest() {

    @Inject
    lateinit var repository: ExerciseRepositoryImpl

    @Test
    fun givenValidStrengthExercise_whenSavedToDatabase_thenShouldBeRetrievable() = runTest {
        repository.addStrengthExercise(exercise())

        val savedExercises = assertResultSuccess { repository.getAllStrengthExercises() }
        assertThat(savedExercises.size).isEqualTo(1)
    }

    @Test
    fun givenInvalidStrengthExercise_whenSavedToDatabase_thenShouldReturnError() = runTest {
        val exerciseToSave = exercise().copy(
            type = ExerciseType.Endurance(
                activityType = null,
                durationUnit = null,
            )
        )
        assertResultError(DataError.Local.MAPPING_ERROR) {
            repository.addStrengthExercise(exerciseToSave)
        }

        val savedExercises = assertResultSuccess { repository.getAllStrengthExercises() }
        assertThat(savedExercises.size).isEqualTo(0)
    }

    @Test
    fun givenStrengthExercise_whenDeleted_thenRemovesFromExerciseAndStrengthExerciseTables() =
        runTest {
            val strengthExerciseToSave = exercise()
            repository.addStrengthExercise(strengthExerciseToSave)

            val savedExercises = assertResultSuccess { repository.getAllStrengthExercises() }
            assertThat(savedExercises.size).isEqualTo(1)

            repository.removeExerciseById(strengthExerciseToSave.id)

            val exercises = db.exerciseDao().getAllExercises()
            assertThat(exercises.size).isEqualTo(0)
            val strengthExercises = db.strengthExerciseDao().getAllStrengthExercises()
            assertThat(strengthExercises.size).isEqualTo(0)
        }

    @Test
    fun givenExercise_whenAddNewExerciseWithSameName_thenShouldReturnError() = runTest {
        val name = "BenchPress"
        val strengthExerciseToSave1 = exercise().copy(
            id = "1",
            name = name
        )
        val strengthExerciseToSave2 = exercise().copy(
            id = "2",
            name = name
        )
        repository.addStrengthExercise(strengthExerciseToSave1)

        val savedExercises1 = assertResultSuccess { repository.getAllStrengthExercises() }
        assertThat(savedExercises1.size).isEqualTo(1)

        assertResultError(DataError.Local.DATA_INCONSISTENCY) {
            repository.addStrengthExercise(strengthExerciseToSave2)
        }

        val savedExercises2 = assertResultSuccess { repository.getAllStrengthExercises() }
        assertThat(savedExercises2.size).isEqualTo(1)
    }
}