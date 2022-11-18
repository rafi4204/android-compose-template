package com.monstarlab.arch.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import timber.log.Timber

abstract class SingleSharedPreferenceDataStore<T> constructor(
    private val dataStore: DataStore<Preferences>,
    private val serializer: KSerializer<T>
) : SingleDataSource<T> {

    private val key = stringPreferencesKey(this.javaClass.simpleName)

    override suspend fun get(): T? {
        return try {
            val json = dataStore.data.map { it[key] ?: "" }.first()
            val entries = Json.decodeFromString(serializer, json)
            entries
        } catch (e: SerializationException) {
            null
        }
    }

    override suspend fun add(item: T) {
        try {
            val json = Json.encodeToString(serializer, item)
            dataStore.edit {
                it[key] = json
            }
        } catch (e: SerializationException) {
            Timber.e(e)
        }
    }

    override suspend fun clear() {
        dataStore.edit { it[key] = "" }
    }
}
