package io.github.c0nnor263.attributionmanager.domain.httpengine

import androidx.annotation.Keep

@Keep
data class HttpEngineResponse(
    val response: String? = null,
    val push: String? = null,
)
