package io.github.c0nnor263.attribution_engine_ktor.domain

import android.util.Log
import io.github.c0nnor263.attribution_engine_ktor.BuildConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

class LoggingDefaultPlugin {
    companion object {
        fun install(config: HttpClientConfig<AndroidEngineConfig>, tag: String) = with(config) {
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.d(tag, message)
                        }
                    }
                    level = LogLevel.ALL
                }
            }
        }
    }
}