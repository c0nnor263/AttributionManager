package io.github.c0nnor263.attribution_engine_ktor.data

import io.github.c0nnor263.attribution_engine_ktor.KtorEngine
import io.github.c0nnor263.attribution_engine_ktor.domain.HttpRequestRetryDefaultPlugin
import io.github.c0nnor263.attribution_engine_ktor.domain.HttpSendDefaultPlugin
import io.github.c0nnor263.attribution_engine_ktor.domain.HttpTimeoutDefaultPlugin
import io.github.c0nnor263.attribution_engine_ktor.domain.LoggingDefaultPlugin
import io.github.c0nnor263.attributionmanager.data.RequestConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.contentType

import io.ktor.serialization.gson.gson

class KtorEngineBuilder {
    lateinit var client: HttpClient
        private set

    var requestConfig: RequestConfig = RequestConfig()
        private set

    fun configure(block: RequestConfig.() -> Unit): KtorEngineBuilder = apply {
        requestConfig.apply(block)
    }

    fun build(): KtorEngine {
        client = createHttpClient()
        return KtorEngine(this)
    }

        private fun createHttpClient(): HttpClient {
            return HttpClient(Android) {
                install(UserAgent) { agent = requestConfig.userAgent.get() }
                install(ContentNegotiation) { gson() }
                install(DefaultRequest) { contentType(ContentType.Application.Json) }

                HttpRequestRetryDefaultPlugin.install(this)
                HttpTimeoutDefaultPlugin.install(this)
                LoggingDefaultPlugin.install(this, TAG)
            }.apply { HttpSendDefaultPlugin.install(this) }
        }

    companion object {
        const val TAG = "KtorEngineBuilder"
    }
}