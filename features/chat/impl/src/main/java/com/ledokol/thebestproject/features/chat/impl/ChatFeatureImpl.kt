package com.ledokol.thebestproject.features.chat.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ledokol.thebestproject.features.chat.api.ChatFeatureApi
import com.ledokol.thebestproject.features.chat.impl.ui.components.InternalChatFeatureRoutes
import com.ledokol.thebestproject.features.chat.impl.ui.components.molecules.ChatsItem
import com.ledokol.thebestproject.features.chat.impl.ui.components.screens.Chat
import com.ledokol.thebestproject.features.chat.impl.ui.components.screens.ChatsList

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