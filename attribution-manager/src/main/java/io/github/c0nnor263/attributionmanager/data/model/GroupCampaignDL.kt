package io.github.c0nnor263.attributionmanager.data.model

import androidx.annotation.Keep
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostNames

@Keep
data class GroupCampaignDL(
    var linkData: String? = null,
    var applicationId: String? = null,
    var accessClientToken: String? = null,
) {
    fun createNamedMap(names: PostNames): Map<String?, Any?> {
        return mapOf(
            names.groupCampaignDLName to linkData,
            names.groupCampaignDLApplicationId to applicationId,
            names.groupCampaignDLAccessClientToken to accessClientToken,
        )
    }
}
