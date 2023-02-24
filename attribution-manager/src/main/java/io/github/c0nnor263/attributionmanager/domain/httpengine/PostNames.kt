package io.github.c0nnor263.attributionmanager.domain.httpengine

import androidx.annotation.Keep
import java.lang.reflect.Field

@Keep
data class PostNames(
    val packageName: String? = null,
    val deviceAdvertisingId: String? = null,
    val referrer: String? = null,
    val developmentSettings: String? = null,
    val chargerStatus: String? = null,

    val groupCampaignDLName: String? = null,
    val groupCampaignDLApplicationId: String? = null,
    val groupCampaignDLAccessClientToken: String? = null,


    val conversionId: String? = null,
    val conversionKey: String? = null,
    val afID: String? = null,
    val afAD: String? = null,
    val afChannel: String? = null,
    val mediaSource: String? = null,
    val adsetId: String? = null,
    val campaign: String? = null,
    val campaignId: String? = null,
    val afStatus: String? = null,
    val adset: String? = null,
) {

    fun isValid(): Boolean {
        return this::class.java.declaredFields.all { field: Field? ->
            field?.get(this) != null && field.get(this)?.toString()?.isNotBlank() == true
        }
    }
}