package io.github.c0nnor263.attribution_engine_ktor.domain

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.http.isSuccess
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.PortUnreachableException
import java.net.ProtocolException
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


class HttpRequestRetryDefaultPlugin {



    companion object{
        fun install(config: HttpClientConfig<AndroidEngineConfig>) = with(config){
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
        }
        const val DEFAULT_DELAY_SECONDS = 5L
        const val DEFAULT_MAX_RETRIES = 5
    }
}