package com.nux.studio.dvor.features.chat.api

import com.nux.studio.dvor.core.feature.api.FeatureApi

interface ChatFeatureApi: FeatureApi {

        fun chatsRoute(): String

        fun chatRoute(): String

}