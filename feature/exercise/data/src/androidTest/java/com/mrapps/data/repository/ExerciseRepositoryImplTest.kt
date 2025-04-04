package com.mrapps.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.mrapps.android_testing.MrFitAndroidTest
import com.mrapps.domain.DataError
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise.type.endurance.EnduranceActivityType
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.domain.units.MeasurementUnit
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
        val exerciseToSave = exercise()
        repository.addExercise(exerciseToSave)

        val savedExercises =
            assertResultSuccess { repository.getExercisesByType(ExerciseTypeEnum.STRENGTH) }
        assertThat(savedExercises.size).isEqualTo(1)
        assertThat(savedExercises[0]).isEqualTo(exerciseToSave)
    }

    @Test
    fun givenValidEnduranceExercise_whenSavedToDatabase_thenShouldBeRetrievable() = runTest {
        val exerciseToSave = exercise().copy(
            type = ExerciseType.Endurance(
                activityType = EnduranceActivityType.RUNNING,
                durationUnit = MeasurementUnit.Time.Minute
            )
        )
        repository.addExercise(exerciseToSave)

        val savedExercises =
            assertResultSuccess { repository.getExercisesByType(ExerciseTypeEnum.ENDURANCE) }
        assertThat(savedExercises.size).isEqualTo(1)
        assertThat(savedExercises[0]).isEqualTo(exerciseToSave)
    }

    @Test
    fun givenStrengthExercise_whenDeleted_thenRemovesFromExerciseAndStrengthExerciseTables() =
        runTest {
            val strengthExerciseToSave = exercise()
            repository.addExercise(strengthExerciseToSave)

            val savedExercises =
                assertResultSuccess { repository.getExercisesByType(ExerciseTypeEnum.STRENGTH) }
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
        repository.addExercise(strengthExerciseToSave1)

        val savedExercises1 =
            assertResultSuccess { repository.getExercisesByType(ExerciseTypeEnum.STRENGTH) }
        assertThat(savedExercises1.size).isEqualTo(1)

        assertResultError(DataError.Local.DATA_INCONSISTENCY) {
            repository.addExercise(strengthExerciseToSave2)
        }

        val savedExercises2 =
            assertResultSuccess { repository.getExercisesByType(ExerciseTypeEnum.STRENGTH) }
        assertThat(savedExercises2.size).isEqualTo(1)
    }

    @Test
    fun givenStrengthExercise_whenUpdatedToEndurance_thenReplacesStrengthWithEndurance() = runTest {
        val name = "BenchPress"
        val initialStrengthExercise = exercise().copy(
            id = "1",
            name = name
        )
        repository.addExercise(initialStrengthExercise)
        val savedStrengthExercises1 =
            assertResultSuccess { repository.getExercisesByType(ExerciseTypeEnum.STRENGTH) }
        val savedEnduranceExercises1 =
            assertResultSuccess { repository.getExercisesByType(ExerciseTypeEnum.ENDURANCE) }
        assertThat(savedStrengthExercises1.size).isEqualTo(1)
        assertThat(savedEnduranceExercises1.size).isEqualTo(0)
        assertThat(savedStrengthExercises1[0].id).isEqualTo(initialStrengthExercise.id)

        repository.updateExercise(
            initialStrengthExercise.copy(
                type = ExerciseType.Endurance(
                    activityType = EnduranceActivityType.RUNNING,
                    durationUnit = MeasurementUnit.Time.Hour
                )
            )
        )

        val savedStrengthExercises2 =
            assertResultSuccess { repository.getExercisesByType(ExerciseTypeEnum.STRENGTH) }
        val savedEnduranceExercises2 =
            assertResultSuccess { repository.getExercisesByType(ExerciseTypeEnum.ENDURANCE) }
        assertThat(savedStrengthExercises2.size).isEqualTo(0)
        assertThat(savedEnduranceExercises2.size).isEqualTo(1)
        assertThat(savedEnduranceExercises2[0].id).isEqualTo(initialStrengthExercise.id)
    }
}