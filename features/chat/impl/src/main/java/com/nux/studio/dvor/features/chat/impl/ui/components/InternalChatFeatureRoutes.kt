package com.nux.studio.dvor.features.chat.impl.ui.components


internal object InternalChatFeatureRoutes {
    private lateinit var chatRoute: String

    fun chat() = chatRoute

    fun registerRoutes(
        chatRoute: String,
    ) {
        InternalChatFeatureRoutes.chatRoute = chatRoute
    }
}