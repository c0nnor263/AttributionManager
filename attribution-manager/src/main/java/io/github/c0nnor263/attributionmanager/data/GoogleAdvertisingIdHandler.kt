package io.github.c0nnor263.attributionmanager.data

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

internal class GoogleAdvertisingIdHandler(private val context: Context) {
    companion object {
        private const val TAG = "GoogleAdvertisingIdHandler"
    }

    suspend fun getGoogleAdvertisingId(): String? = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { cancellableContinuation ->
            try {
                val advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                cancellableContinuation.resume(advertisingIdInfo.id)
            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "getGoogleAdvertisingId:",
                    e
                )
                cancellableContinuation.resume(null)
            }
        }
    }
}