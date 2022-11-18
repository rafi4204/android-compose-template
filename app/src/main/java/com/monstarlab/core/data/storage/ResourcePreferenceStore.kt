package com.monstarlab.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.monstarlab.arch.data.SharedPreferenceDataStore
import com.monstarlab.core.domain.model.Resource
import javax.inject.Inject

class ResourcePreferenceStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : SharedPreferenceDataStore<Resource>(dataStore, Resource.serializer())
