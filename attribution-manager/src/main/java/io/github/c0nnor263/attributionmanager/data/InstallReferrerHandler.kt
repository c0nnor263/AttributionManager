package io.github.c0nnor263.attributionmanager.data

import android.content.Context
import android.util.Log
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerClient.InstallReferrerResponse
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

internal class InstallReferrerHandler(context: Context) {
    companion object {
        private const val TAG = "InstallReferrerHandler"
    }

    private val referrerClient = InstallReferrerClient.newBuilder(context).build()

    suspend fun getReferrer(): String? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            val listener: InstallReferrerStateListener = object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    Log.i(TAG, "onInstallReferrerSetupFinished response code $responseCode")
                    when (responseCode) {
                        InstallReferrerResponse.OK -> {
                            try {
                                val response: ReferrerDetails? = referrerClient.installReferrer
                                val referrer: String? = response?.installReferrer
                                cancellableContinuation.resume(referrer)
                            } catch (e: Exception) {
                                cancellableContinuation.resume(null)
                            }
                        }

                        else -> {
                            cancellableContinuation.resume(null)
                        }
                    }
                }

                override fun onInstallReferrerServiceDisconnected() {
                    Log.e(TAG, "onInstallReferrerServiceDisconnected")
                    cancellableContinuation.resume(null)
                }

            }
            referrerClient.startConnection(listener)

            cancellableContinuation.invokeOnCancellation {
                referrerClient.endConnection()
            }
        }
    }
}