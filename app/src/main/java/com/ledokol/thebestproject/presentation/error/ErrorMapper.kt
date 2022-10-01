package com.ledokol.thebestproject.presentation.error

import android.content.Context
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.error.ErrorRemote

class ErrorMapper(
    private val context: Context
) {

    fun map(error: ErrorRemote?): String? {

        val message : String? = when(error){
            is ErrorRemote.NoInternet -> {
                context.getString(R.string.error_no_internet)
            }
            is ErrorRemote.BadRequest -> {
//                context.getString(R.string.error_bad_request)
                null
            }
            is ErrorRemote.Unauthorized -> {
                context.getString(R.string.error_unauthorized)
            }
            is ErrorRemote.Forbidden -> {
                context.getString(R.string.error_forbidden)
            }
            is ErrorRemote.NotFound -> {
                context.getString(R.string.error_not_found)
            }
            is ErrorRemote.InternalServerError -> {
//                context.getString(R.string.error_no_internet)
                null
            }
            is ErrorRemote.BadGateway -> {
//                context.getString(R.string.error_no_internet)
                null
            }
            is ErrorRemote.ServiceUnavailable -> {
//                context.getString(R.string.error_no_internet)
                null
            }
            is ErrorRemote.GatewayTimeout -> {
//                context.getString(R.string.error_no_internet)
                null
            }
            is ErrorRemote.Unknown -> {
//                context.getString(R.string.error_no_internet)
                null
            }
            else -> {
                null
            }
        }

        return message

    }

}