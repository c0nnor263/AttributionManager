package io.github.c0nnor263.attributionmanager.data

import android.os.Build
import java.util.Locale

class UserAgent {
    fun get(): String = try {
        System.getProperty("http.agent") ?: default()
    } catch (e: Exception) {
        default()
    }


    fun default(): String {
        val version = Build.VERSION.RELEASE
        val locale = Locale.getDefault()
        val model = Build.MODEL
        val id = Build.ID

        return "(Android " +
                "$version; " +
                "$locale; " +
                "$model; " +
                "Build/ + $id" +
                ")"
    }
}
