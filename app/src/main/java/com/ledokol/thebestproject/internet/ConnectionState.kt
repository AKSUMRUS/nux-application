package com.ledokol.thebestproject.internet

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}