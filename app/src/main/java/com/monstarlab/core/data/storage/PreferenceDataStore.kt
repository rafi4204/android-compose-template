package com.monstarlab.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.monstarlab.core.data.storage.PreferenceDataStore.PreferencesKeys.PREF_LOGGED_IN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        // todo change the name accordingly
        const val PREFS_NAME = "app_prefs"
    }

    object PreferencesKeys {
        val PREF_LOGGED_IN = booleanPreferencesKey("pref_logged_in")
    }

    suspend fun completeLogin(complete: Boolean) {
        dataStore.edit {
            it[PREF_LOGGED_IN] = complete
        }
    }

    val isLoggedIn: Flow<Boolean> =
        dataStore.data.map { it[PREF_LOGGED_IN] ?: false }

}
