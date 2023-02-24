package io.github.c0nnor263.attributionmanager.data.model

import androidx.annotation.Keep

@Keep
data class UserRelatedInfo(
    val googleAdvertisingId: String?,
    val referrer: String?
)
