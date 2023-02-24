package io.github.c0nnor263.attribution_engine_ktor.domain.config

import android.os.Build
import androidx.annotation.Keep
import io.github.c0nnor263.attributionmanager.domain.httpengine.HttpEngineProtocol
import java.util.Locale


@Keep
data class RequestConfig(
    var protocol:HttpEngineProtocol = HttpEngineProtocol.HTTP,
    var host: String? = null,
    var path: String? = null,
    var userAgent: String = try {
        System.getProperty("http.agent") ?: defaultUserAgent()
    } catch (e: Exception) {
        defaultUserAgent()
    },
) {
    internal fun isValid(): Boolean {
        return checkValue(host) && checkValue(path)
    }

    private fun checkValue(value: String?): Boolean {
        return value.isNullOrBlank().not()
    }

    companion object {
        fun defaultUserAgent(): String {
            val version = Build.VERSION.RELEASE
            val locale = Locale.getDefault()
            val model = Build.MODEL
            val id = Build.ID

            return "(Android " +
                    "$version; " +
                    "$locale; " +
                    "$model; " +
                    "Build/ + $id" +
                    ")"
        }
    }
}
