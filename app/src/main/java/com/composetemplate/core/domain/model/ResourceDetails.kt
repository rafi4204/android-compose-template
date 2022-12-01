package com.composetemplate.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ResourceDetails(
    val id: Int,
    val name: String,
    val imageUrl: String
) : Parcelable