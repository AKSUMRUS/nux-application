package com.nux.studio.dvor.data.error

sealed class ErrorRemote {
    object NoInternet : ErrorRemote()
    object BadRequest : ErrorRemote()
    object Unauthorized : ErrorRemote()
    object Forbidden : ErrorRemote()
    object NotFound : ErrorRemote()
    object InternalServerError : ErrorRemote()
    object BadGateway : ErrorRemote()
    object ServiceUnavailable : ErrorRemote()
    object GatewayTimeout : ErrorRemote()
    object Unknown : ErrorRemote()
}