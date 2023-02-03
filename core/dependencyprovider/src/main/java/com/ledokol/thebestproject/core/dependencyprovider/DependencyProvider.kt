package com.ledokol.thebestproject.core.dependencyprovider

import com.ledokol.thebestproject.features.chat.api.ChatFeatureApi

object DependencyProvider {

    private lateinit var chatFeatureApi: ChatFeatureApi

    fun provideImpl(
        chatFeatureApi: ChatFeatureApi
    ) {
        this.chatFeatureApi = chatFeatureApi
    }

    fun chatFeature() : ChatFeatureApi = chatFeatureApi

}