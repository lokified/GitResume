package com.loki.gitresume.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val TEST_DATASTORE_NAME: String = "test_datastore"

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DataStoreStorageTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope =
        TestCoroutineScope(testCoroutineDispatcher + Job())

    private val testContext: Context = ApplicationProvider.getApplicationContext()

    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile = {
                testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME)
            }
        )

    private val dataStoreStorage: DataStoreStorageImpl = DataStoreStorageImpl(testDataStore)

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @Test
    fun saveLoggedIn_savesAsTrue() = runTest {
        dataStoreStorage.saveLoggedIn(true)
        assertTrue(dataStoreStorage.getLoggedIn().first())
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        runTest {
            testDataStore.edit { it.clear() }
        }
        testCoroutineScope.cancel()
    }
}