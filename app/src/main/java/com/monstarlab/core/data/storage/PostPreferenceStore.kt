package com.monstarlab.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.monstarlab.arch.data.SharedPreferenceDataStore
import com.monstarlab.core.domain.model.Post
import javax.inject.Inject

class PostPreferenceStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : SharedPreferenceDataStore<Post>(dataStore, Post.serializer())
