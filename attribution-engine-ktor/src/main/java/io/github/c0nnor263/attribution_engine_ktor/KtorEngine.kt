package io.github.c0nnor263.attribution_engine_ktor

import android.util.Log
import io.github.c0nnor263.attribution_engine_ktor.domain.config.RequestConfig
import io.github.c0nnor263.attributionmanager.domain.ConversionListener
import io.github.c0nnor263.attributionmanager.domain.httpengine.HttpEngine
import io.github.c0nnor263.attributionmanager.domain.httpengine.HttpEngineResponse
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostBody
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostNames
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.serialization.gson.gson
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.PortUnreachableException
import java.net.ProtocolException
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class KtorEngine private constructor(
    private val client: HttpClient,
    private val requestConfig: RequestConfig
) : HttpEngine {
    private constructor(builder: Builder) : this(builder.client, builder.requestConfig)

    override suspend fun post(body: PostBody, names: PostNames, callback: ConversionListener) {
        require(names.isValid()) {
            "$TAG: parameters names can't be null or blank"
        }
        require(requestConfig.isValid()) {
            "$TAG: host and path can't be null or blank"
        }

        try {
            val response: HttpEngineResponse = client.post {
                url {
                    protocol = URLProtocol.createOrDefault(requestConfig.protocol.name)
                    host = requestConfig.host.toString()
                    path(requestConfig.path.toString())
                    setBody(body.createNamedBody(names))
                }
            }.body()

            callback.onSuccess(response)
        } catch (e: Throwable) {
            callback.onFailure(e)
        }
    }


    class Builder {
        lateinit var client: HttpClient
            private set

        var requestConfig: RequestConfig = RequestConfig()
            private set

        fun configure(block: RequestConfig.() -> Unit): Builder = apply {
            requestConfig.apply(block)
        }

        fun build(): KtorEngine {
            client = createHttpClient()
            return KtorEngine(this)
        }


        private fun createHttpClient(): HttpClient {
            return HttpClient(Android) {
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

                install(HttpRequestRetry) {
                    maxRetries = DEFAULT_MAX_RETRIES
                    retryOnException(DEFAULT_MAX_RETRIES)
                    retryOnServerErrors(DEFAULT_MAX_RETRIES)
                    retryIf { _, response ->
                        response.status.isSuccess().not()
                    }
                    retryOnExceptionIf { _, cause ->
                        (cause is UnknownHostException) or
                                (cause is java.net.SocketTimeoutException) or
                                (cause is ConnectException) or
                                (cause is NoRouteToHostException) or
                                (cause is PortUnreachableException) or
                                (cause is ProtocolException) or
                                (cause is SocketException) or
                                (cause is Exception)
                    }

                    delayMillis { retry ->
                        retry * TimeUnit.SECONDS.toMillis(DEFAULT_DELAY_SECONDS)
                    }
                }


                install(UserAgent) { agent = requestConfig.userAgent }
                install(ContentNegotiation) { gson() }
                install(DefaultRequest) { contentType(ContentType.Application.Json) }

                if (BuildConfig.DEBUG) {
                    install(Logging) {
                        logger = object : Logger {
                            override fun log(message: String) {
                                Log.d(TAG, message)
                            }
                        }
                        level = LogLevel.ALL
                    }
                }

            }.apply {
                plugin(HttpSend).intercept { request ->
                    val originalCall = execute(request)
                    if (originalCall.response.status.value !in 100..399) {
                        execute(request)
                    } else originalCall
                }
            }
        }
    }


    companion object {
        const val DEFAULT_TIMEOUT_SECONDS = 120L
        const val DEFAULT_DELAY_SECONDS = 5L
        const val DEFAULT_MAX_RETRIES = 5

        const val TAG = "KtorEngine"

    }
}
