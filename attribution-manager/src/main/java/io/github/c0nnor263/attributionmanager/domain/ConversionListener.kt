package io.github.c0nnor263.attributionmanager.domain

import io.github.c0nnor263.attributionmanager.domain.httpengine.HttpEngineResponse

interface ConversionListener {
    fun onSuccess(response: HttpEngineResponse)
    fun onFailure(exception: Throwable)
}