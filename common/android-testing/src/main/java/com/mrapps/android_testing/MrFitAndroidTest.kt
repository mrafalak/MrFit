package com.mrapps.android_testing

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mrapps.data.local.AppDatabase
import com.mrapps.domain.util.DispatcherProvider
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class MrFitAndroidTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    protected lateinit var context: Context

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    open fun setUp() {
        hiltRule.inject()
        context = ApplicationProvider.getApplicationContext()
        Dispatchers.setMain(mainCoroutineRule.testDispatcher)

        if (::db.isInitialized) {
            db.clearAllTables()
        }
    }

    @After
    open fun tearDown() {
        if (::db.isInitialized && db.isOpen) {
            db.close()
        }
    }
}