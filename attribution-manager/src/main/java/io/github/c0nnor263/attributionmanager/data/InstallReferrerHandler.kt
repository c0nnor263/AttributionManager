package io.github.c0nnor263.attributionmanager.data

import android.content.Context
import android.util.Log
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class InstallReferrerHandler(context: Context) {
    companion object{
        private const val TAG = "InstallReferrerHandler"
    }
    private val referrerClient = InstallReferrerClient.newBuilder(context).build()

    @Throws(Throwable::class)
    suspend fun getRefferer(): String? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            val listener: InstallReferrerStateListener = object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(p0: Int) {
                    val response: ReferrerDetails? = referrerClient.installReferrer
                    val referrer: String? = response?.installReferrer
                    cancellableContinuation.resume(referrer)
                }

                override fun onInstallReferrerServiceDisconnected() {
                    Log.e(TAG, "onInstallReferrerServiceDisconnected" )
                    cancellableContinuation.resumeWithException(Throwable("InstallReferrerServiceDisconnected"))
                }

            }
            referrerClient.startConnection(listener)

            cancellableContinuation.invokeOnCancellation {
                referrerClient.endConnection()
            }
        }
    }
}