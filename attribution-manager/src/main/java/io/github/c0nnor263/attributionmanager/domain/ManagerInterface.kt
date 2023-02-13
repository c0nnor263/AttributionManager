package io.github.c0nnor263.attributionmanager.domain

internal interface ManagerInterface<T, C> {
    fun configure(block: C.() -> Unit): T
    fun start(listener:ConversionListener): T
}