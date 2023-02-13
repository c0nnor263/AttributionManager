package io.github.c0nnor263.attributionmanager.data

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class GoogleAdvertisingIdHandler(private val context: Context) {
    companion object {
        private const val TAG = "GoogleAdvertisingIdHandler"
    }

    suspend fun getGoogleAdvertisingId(): String? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            try {
                val advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                if (advertisingIdInfo.isLimitAdTrackingEnabled.not()) {
                    cancellableContinuation.resume(advertisingIdInfo.id)
                } else {
                    cancellableContinuation.resumeWithException(IOException("LimitAdTrackingEnabled"))
                }
            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "getGoogleAdvertisingId:",
                    e
                )
                cancellableContinuation.resumeWithException(e)
            }
        }
    }
}