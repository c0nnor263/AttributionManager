package io.github.c0nnor263.attributionmanager.data.model

import androidx.annotation.Keep
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostNames

@Keep
data class AttributionData(
    var host: String? = null,
    val developmentSettings: Boolean,
    val chargerStatus: Float,
    val packageName: String,
    var userRelatedInfo: UserRelatedInfo? = null
) {

    fun createNamedMap(names: PostNames): Map<String?, Any?> {
        return mapOf(
            names.packageName to packageName,
            names.referrer to userRelatedInfo?.referrer,
            names.deviceAdvertisingId to userRelatedInfo?.googleAdvertisingId,
            names.chargerStatus to chargerStatus.toString(),
            names.developmentSettings to developmentSettings,
        )
    }
}
