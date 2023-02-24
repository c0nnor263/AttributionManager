package io.github.c0nnor263.attributionmanager.domain.httpengine

import androidx.annotation.Keep
import io.github.c0nnor263.attributionmanager.data.model.AttributionData
import io.github.c0nnor263.attributionmanager.data.model.GroupCampaignConversion
import io.github.c0nnor263.attributionmanager.data.model.GroupCampaignDL

@Keep
data class PostBody(
    val attributionData: AttributionData,
    var groupCampaignDL: GroupCampaignDL,
    var groupCampaignConversion: GroupCampaignConversion,
) {
    fun createNamedBody(names: PostNames): Map<String?, Any?> {
        val attributionDataMap = attributionData.createNamedMap(names)
        val groupCampaignDLMap = groupCampaignDL.createNamedMap(names)
        val groupCampaignConversionMap = groupCampaignConversion.createNamedMap(names)
        return attributionDataMap.plus(groupCampaignDLMap).plus(groupCampaignConversionMap)
    }
}
