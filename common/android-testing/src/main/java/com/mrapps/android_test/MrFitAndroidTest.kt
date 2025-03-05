package com.mrapps.android_test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mrapps.data.local.AppDatabase
import com.mrapps.domain.util.DispatcherProvider
import com.mrapps.test.TestDispatchers
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class MrFitAndroidTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var hiltRule: HiltAndroidRule

    @Inject
    lateinit var db: AppDatabase

    protected lateinit var context: Context

    lateinit var dispatcherProvider: DispatcherProvider

    @Before
    open fun setUp() {
        hiltRule = HiltAndroidRule(this)
        hiltRule.inject()

        context = ApplicationProvider.getApplicationContext()
        dispatcherProvider = TestDispatchers(mainCoroutineRule.testDispatcher)

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