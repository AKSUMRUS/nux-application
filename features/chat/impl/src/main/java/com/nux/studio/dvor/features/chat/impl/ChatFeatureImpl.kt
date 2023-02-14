package com.nux.studio.dvor.features.chat.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nux.studio.dvor.features.chat.api.ChatFeatureApi
import com.nux.studio.dvor.features.chat.impl.ui.components.InternalChatFeatureRoutes
import com.nux.studio.dvor.features.chat.impl.ui.components.screens.Chat
import com.nux.studio.dvor.features.chat.impl.ui.components.screens.ChatsList

class ChatFeatureImpl : ChatFeatureApi {
    private val baseRoute = "chat"

    override fun chatsRoute(): String = baseRoute

    override fun chatRoute(): String = "$baseRoute/1"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        InternalChatFeatureRoutes.registerRoutes(
            chatRoute = chatRoute()
        )

        navGraphBuilder.composable(chatsRoute()) {
                ChatsList(navController = navController)
            }

        navGraphBuilder.composable(chatRoute()) {
                Chat()
            }

    }

}