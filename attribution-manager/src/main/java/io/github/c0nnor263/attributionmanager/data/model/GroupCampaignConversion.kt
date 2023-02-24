package io.github.c0nnor263.attributionmanager.data.model

import androidx.annotation.Keep
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostNames
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Keep
data class GroupCampaignConversion(
    var conversionUserId: String? = null,
    var conversionKey: String? = null,
    var isEnabled: Boolean = false,
    var conversion: Map<String, Any?>? = null,
) {

    fun createNamedMap(names: PostNames): Map<String?, Any?> {
        require(conversionUserId.isNullOrBlank().not()){
            "$TAG: Conversion key can't be null or blank"
        }

        return mapOf(
            names.conversionId to conversionUserId,
            names.conversionKey to conversionKey,
            names.campaign to getEncodedConversion(names),
        )
    }

    private fun getEncodedConversion(names: PostNames): Map<String?, Any?>? {
        if (isEnabled.not() || conversion == null) return null

        return mapOf(
            names.afID to conversion?.get("ad_id")?.toString()?.encode(),
            names.afAD to conversion?.get("af_ad")?.toString()?.encode(),
            names.afStatus to conversion?.get("af_status")?.toString()?.encode(),
            names.afChannel to conversion?.get("af_channel")?.toString()?.encode(),
            names.mediaSource to conversion?.get("media_source")?.toString()?.encode(),
            names.adset to conversion?.get("adset")?.toString()?.encode(),
            names.adsetId to conversion?.get("adset_id")?.toString()?.encode(),
            names.campaign to conversion?.get("campaign")?.toString()?.encode(),
            names.campaignId to conversion?.get("campaign_id")?.toString()?.encode(),
        )
    }

    companion object {
        fun String?.encode(): String? {
            return try {
                URLEncoder.encode(this, StandardCharsets.UTF_8.displayName())
            } catch (e: Exception) {
                this
            }
        }
        const val TAG = "GroupCampaignConversion"
    }
}

