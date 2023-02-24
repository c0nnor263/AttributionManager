package io.github.c0nnor263.attributionmanager.domain.httpengine

import io.github.c0nnor263.attributionmanager.domain.ConversionListener

interface HttpEngine {
    suspend fun post(
        body: PostBody,
        names: PostNames,
        callback: ConversionListener
    )
}