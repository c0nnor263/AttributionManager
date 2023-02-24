package io.github.c0nnor263.attributionmanager.data

import androidx.annotation.Keep
import io.github.c0nnor263.attributionmanager.domain.httpengine.HttpEngineProtocol

@Keep
data class RequestConfig(
    var protocol: HttpEngineProtocol = HttpEngineProtocol.HTTP,
    var host: String? = null,
    var path: String? = null,
    var userAgent: UserAgent = UserAgent(),
) {
     fun isValid(): Boolean {
        return checkValue(host) && checkValue(path)
    }

    private fun checkValue(value: String?): Boolean {
        return value.isNullOrBlank().not()
    }
    
}