package io.github.c0nnor263.attributionmanager

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.github.c0nnor263.attributionmanager.data.GoogleAdvertisingIdHandler
import io.github.c0nnor263.attributionmanager.data.InstallReferrerHandler
import io.github.c0nnor263.attributionmanager.data.model.AttributionData
import io.github.c0nnor263.attributionmanager.data.model.UserRelatedInfo
import io.github.c0nnor263.attributionmanager.domain.ConversionListener
import io.github.c0nnor263.attributionmanager.domain.ManagerInterface
import io.github.c0nnor263.attributionmanager.domain.config.AttributionManagerConfig
import io.github.c0nnor263.attributionmanager.extensions.scope


class AttributionManager(
    private val activity: ComponentActivity
) : DefaultLifecycleObserver,
    ManagerInterface<AttributionManager, AttributionManagerConfig>,
    LifecycleOwner {
    private var config = AttributionManagerConfig()
    private var attributionData: AttributionData? = null

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

    override fun configure(block: AttributionManagerConfig.() -> Unit): AttributionManager {
        config.apply(block)

        return this
    }

    override fun start(listener: ConversionListener): AttributionManager {
        scope {
            val storeDetails = InstallReferrerHandler(activity).getRefferer()
            val googleAdvertisingId = GoogleAdvertisingIdHandler(activity).getGoogleAdvertisingId()
            val userRelatedInfo = UserRelatedInfo(googleAdvertisingId, storeDetails)
            config.httpEngine?.post(userRelatedInfo, attributionData, listener)
        }
        return this
    }


    fun passAttributionData(attributionData: AttributionData) {
        this.attributionData = attributionData
    }


    // TODO OneSignal sendTag
}

