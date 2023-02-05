package com.ledokol.dvor.internet

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}