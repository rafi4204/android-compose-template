package com.monstarlab.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.monstarlab.arch.data.SingleSharedPreferenceDataStore
import com.monstarlab.core.domain.model.User
import javax.inject.Inject

class UserPreferenceStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : SingleSharedPreferenceDataStore<User>(dataStore, User.serializer())
