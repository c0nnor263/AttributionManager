package io.github.c0nnor263.attribution_engine_ktor.domain

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.HttpTimeout
import java.util.concurrent.TimeUnit

class HttpTimeoutDefaultPlugin {
    companion object {
        fun install(config: HttpClientConfig<AndroidEngineConfig>) = with(config) {
            engine {
                connectTimeout = 0
                socketTimeout = 0
            }
            install(HttpTimeout) {
                val timeout = TimeUnit.SECONDS.toMillis(DEFAULT_TIMEOUT_SECONDS)
                requestTimeoutMillis = timeout
                connectTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
        }

        const val DEFAULT_TIMEOUT_SECONDS = 120L
    }
}