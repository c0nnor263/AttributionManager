package io.github.c0nnor263.attributionmanager.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

internal fun LifecycleOwner.scope(block: suspend () -> Unit) {
    lifecycleScope.launch {
        block()
    }
}