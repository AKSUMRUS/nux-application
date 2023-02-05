package com.ledokol.dvor.ui.components.molecules

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ledokol.dvor.ui.components.atoms.LoadingView

@Composable
fun LoadingViewCenter() {
    Box(modifier = Modifier.fillMaxSize()) {
        LoadingView(modifier = Modifier.align(Alignment.Center))
    }
}