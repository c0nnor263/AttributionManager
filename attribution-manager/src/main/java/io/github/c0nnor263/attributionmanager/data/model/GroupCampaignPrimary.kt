package io.github.c0nnor263.attributionmanager.data.model

data class GroupCampaignPrimary(
    var conversionUserId:String? = null,
    var conversionKey:String? = null,
    var isEnabled: Boolean = false,
    var conversion: Map<String, Any?>? = null,
)
