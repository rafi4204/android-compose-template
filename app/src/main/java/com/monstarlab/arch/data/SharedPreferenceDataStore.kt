package com.monstarlab.arch.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

abstract class SharedPreferenceDataStore<T> constructor(
    private val dataStore: DataStore<Preferences>,
    private val serializer: KSerializer<T>
) : DataSource<T> {

    private val key = stringPreferencesKey(this.javaClass.simpleName)

    override suspend fun getAll(): List<T> {
        return try {
            val json = dataStore.data.map { it[key] ?: "" }.first()
            Json.decodeFromString(ListSerializer(serializer), json)
        } catch (e: SerializationException) {
            emptyList<T>()
        }

    }

    override suspend fun add(item: T) {
        val list = getAll().toMutableList()
        list.add(item)
        addAll(list)
    }

    override suspend fun addAll(items: List<T>) {
        val json = Json.encodeToString(ListSerializer(serializer), items)
        dataStore.edit {
            it[key] = json
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it[key] = ""
        }
    }
}
