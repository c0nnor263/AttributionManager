package io.github.c0nnor263.attributionmanager.domain

interface ConversionListener {
    fun onSuccess(response: String?)
    fun onFailure(exception: Exception)
}