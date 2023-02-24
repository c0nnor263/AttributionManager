package io.github.c0nnor263.attribution_engine_ktor

import io.github.c0nnor263.attribution_engine_ktor.data.KtorEngineBuilder
import io.github.c0nnor263.attributionmanager.data.RequestConfig
import io.github.c0nnor263.attributionmanager.domain.ConversionListener
import io.github.c0nnor263.attributionmanager.domain.httpengine.HttpEngine
import io.github.c0nnor263.attributionmanager.domain.httpengine.HttpEngineResponse
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostBody
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostNames
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.URLProtocol
import io.ktor.http.path

class KtorEngine private constructor(
    private val client: HttpClient,
    private val requestConfig: RequestConfig
) : HttpEngine {
    constructor(builder: KtorEngineBuilder) : this(builder.client, builder.requestConfig)

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


    companion object {
        const val TAG = "KtorEngine"
    }
}
