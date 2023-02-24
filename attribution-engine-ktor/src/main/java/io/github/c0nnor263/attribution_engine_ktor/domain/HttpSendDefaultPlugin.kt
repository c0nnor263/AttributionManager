package io.github.c0nnor263.attribution_engine_ktor.domain

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin

class HttpSendDefaultPlugin {
    companion object {
        fun install(client: HttpClient) = with(client) {
            plugin(HttpSend).intercept { request ->
                val originalCall = execute(request)
                if (originalCall.response.status.value !in 100..399) {
                    execute(request)
                } else originalCall
            }
        }
    }
}