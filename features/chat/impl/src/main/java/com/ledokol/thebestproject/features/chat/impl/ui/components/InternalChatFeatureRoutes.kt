package com.ledokol.thebestproject.features.chat.impl.ui.components


internal object InternalChatFeatureRoutes {
    private lateinit var chatRoute: String

    fun chat() = chatRoute

    fun registerRoutes(
        chatRoute: String,
    ) {
        this.chatRoute = chatRoute
    }
}