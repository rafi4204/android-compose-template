package com.composetemplate.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.composetemplate.arch.data.SharedPreferenceDataStore
import com.composetemplate.core.domain.model.Post
import javax.inject.Inject

class PostPreferenceStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : SharedPreferenceDataStore<Post>(dataStore, Post.serializer())