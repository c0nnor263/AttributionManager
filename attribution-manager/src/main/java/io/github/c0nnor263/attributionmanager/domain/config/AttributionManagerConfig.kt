package io.github.c0nnor263.attributionmanager.domain.config

import androidx.annotation.Keep
import io.github.c0nnor263.attributionmanager.data.model.AttributionData
import io.github.c0nnor263.attributionmanager.data.model.GroupCampaignConversion
import io.github.c0nnor263.attributionmanager.data.model.GroupCampaignDL
import io.github.c0nnor263.attributionmanager.domain.httpengine.HttpEngine
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostNames

@Keep
data class AttributionManagerConfig(
    var httpEngine: HttpEngine? = null,
    var attributionData: AttributionData? = null,
    var groupCampaignConversion: GroupCampaignConversion? = null,
    var groupCampaignDL: GroupCampaignDL? = null,
    var postNames: PostNames = PostNames(),
)