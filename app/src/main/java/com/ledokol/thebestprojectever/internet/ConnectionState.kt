package com.ledokol.thebestprojectever.internet

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}