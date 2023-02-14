package com.nux.studio.dvor.util

import com.nux.studio.dvor.data.error.ErrorRemote

sealed class Resource<T>(
    val data: T? = null,
    val message: ErrorRemote? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: ErrorRemote?, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}