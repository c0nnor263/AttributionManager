package io.github.c0nnor263.attributionmanager.domain.httpengine

import io.github.c0nnor263.attributionmanager.data.model.AttributionData
import io.github.c0nnor263.attributionmanager.data.model.UserRelatedInfo
import io.github.c0nnor263.attributionmanager.domain.ConversionListener

interface HttpEngine {
    fun post(
        userRelatedInfo: UserRelatedInfo,
        body: AttributionData?,
        callback: ConversionListener
    )
}