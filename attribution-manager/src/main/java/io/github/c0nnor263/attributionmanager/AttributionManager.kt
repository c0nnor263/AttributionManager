package io.github.c0nnor263.attributionmanager

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.github.c0nnor263.attributionmanager.data.GoogleAdvertisingIdHandler
import io.github.c0nnor263.attributionmanager.data.InstallReferrerHandler
import io.github.c0nnor263.attributionmanager.data.model.AttributionData
import io.github.c0nnor263.attributionmanager.data.model.GroupCampaignConversion
import io.github.c0nnor263.attributionmanager.data.model.GroupCampaignDL
import io.github.c0nnor263.attributionmanager.data.model.UserRelatedInfo
import io.github.c0nnor263.attributionmanager.domain.ConversionListener
import io.github.c0nnor263.attributionmanager.domain.ManagerInterface
import io.github.c0nnor263.attributionmanager.domain.config.AttributionManagerConfig
import io.github.c0nnor263.attributionmanager.domain.httpengine.HttpEngine
import io.github.c0nnor263.attributionmanager.domain.httpengine.PostBody
import io.github.c0nnor263.attributionmanager.extensions.scope


class AttributionManager(
    private val activity: ComponentActivity
) : DefaultLifecycleObserver, ManagerInterface<AttributionManager, AttributionManagerConfig>,
    LifecycleOwner {
    private var config = AttributionManagerConfig()

    init {
        lifecycle.addObserver(this)
    }

    override fun getLifecycle(): Lifecycle {
        return activity.lifecycle
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        activity.lifecycle.removeObserver(this)
    }

    override fun configure(block: AttributionManagerConfig.() -> Unit): AttributionManager = apply {
        config.apply(block)
    }

    override fun start(listener: ConversionListener): AttributionManager {
        scope {
            val attributionData = requireNotNull(config.attributionData) {
                "$TAG: ${AttributionData::class.java.simpleName} can't be null"
            }
            val groupCampaignDL = requireNotNull(config.groupCampaignDL) {
                "$TAG: ${GroupCampaignDL::class.java.simpleName} can't be null"
            }
            val groupCampaignConversion = requireNotNull(config.groupCampaignConversion) {
                "$TAG: ${GroupCampaignConversion::class.java.simpleName} can't be null"
            }

            val httpEngine = requireNotNull(config.httpEngine) {
                "$TAG: ${HttpEngine::class.java} can't be null"
            }


            val storeDetails = InstallReferrerHandler(activity).getReferrer()
            val googleAdvertisingId = GoogleAdvertisingIdHandler(activity).getGoogleAdvertisingId()
            val userRelatedInfo = UserRelatedInfo(googleAdvertisingId, storeDetails)
            attributionData.userRelatedInfo = userRelatedInfo

            val body = PostBody(attributionData, groupCampaignDL, groupCampaignConversion)
            httpEngine.post(body, config.postNames, listener)

        }
        return this
    }

    companion object{
        const val TAG = "AttributionManager"
    }
}

