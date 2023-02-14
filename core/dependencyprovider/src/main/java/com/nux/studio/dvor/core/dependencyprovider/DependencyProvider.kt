package com.nux.studio.dvor.core.dependencyprovider

import com.nux.studio.dvor.features.chat.api.ChatFeatureApi

object DependencyProvider {

    private lateinit var chatFeatureApi: ChatFeatureApi

    fun provideImpl(
        chatFeatureApi: ChatFeatureApi
    ) {
        DependencyProvider.chatFeatureApi = chatFeatureApi
    }

    fun chatFeature() : ChatFeatureApi = chatFeatureApi

}