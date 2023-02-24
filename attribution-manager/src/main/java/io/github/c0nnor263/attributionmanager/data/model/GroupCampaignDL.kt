package io.github.c0nnor263.attributionmanager.data.model

import androidx.annotation.Keep
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostNames

@Keep
data class GroupCampaignDL(
    var name: String? = null,
    var applicationId: String? = null,
    var accessClientToken: String? = null,
) {
    fun createNamedMap(names: PostNames): Map<String?, Any?> {
        return mapOf(
            names.groupCampaignDLName to name,
            names.groupCampaignDLApplicationId to applicationId,
            names.groupCampaignDLAccessClientToken to accessClientToken,
        )
    }
}
