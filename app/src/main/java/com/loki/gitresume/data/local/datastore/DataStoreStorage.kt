package com.loki.gitresume.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow

interface DataStoreStorage {

    suspend fun saveLoggedIn(loggedIn: Boolean)

    suspend fun getLoggedIn(): Flow<Boolean>

    object UserPreferences {
        val USER_LOGGEDIN_KEY = booleanPreferencesKey("user_loggedin_key")
    }

}