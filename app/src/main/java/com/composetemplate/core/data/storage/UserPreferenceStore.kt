package com.composetemplate.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.composetemplate.arch.data.SharedPreferenceDataStore
import com.composetemplate.core.domain.model.User
import javax.inject.Inject

class UserPreferenceStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : SharedPreferenceDataStore<User>(dataStore, User.serializer())