package com.loki.gitresume.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.loki.gitresume.data.local.datastore.DataStoreStorage.UserPreferences.USER_LOGGEDIN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreStorageImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
): DataStoreStorage {

    override suspend fun saveLoggedIn(loggedIn: Boolean) {
        datastore.edit { preference ->
            preference[USER_LOGGEDIN_KEY] = loggedIn
        }
    }

    override suspend fun getLoggedIn(): Flow<Boolean> {
        return datastore.data.map { preference ->
            preference[USER_LOGGEDIN_KEY] ?: false
        }
    }
}