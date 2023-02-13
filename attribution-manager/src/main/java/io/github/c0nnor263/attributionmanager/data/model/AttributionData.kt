package io.github.c0nnor263.attributionmanager.data.model

data class AttributionData(
    var host: String? = null,
    var pushId: String? = null,
    var groupCampaignPrimary: GroupCampaignPrimary? = null,
    var groupCampaignSecondary: GroupCampaignSecondary? = null,
    var deviceRelatedInfo: DeviceRelatedInfo? = null,
)
