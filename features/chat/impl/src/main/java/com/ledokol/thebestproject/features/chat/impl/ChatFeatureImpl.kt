package com.ledokol.thebestproject.features.chat.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ledokol.thebestproject.features.chat.api.ChatFeatureApi
import com.ledokol.thebestproject.features.chat.impl.ui.components.screens.Chats

class ChatFeatureImpl : ChatFeatureApi {
    private val baseRoute = "chat"

    override fun chatRoute(): String = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            Chats()
        }
    }

}