package com.nux.studio.dvor.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.R
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH4

@Composable
fun NotInternet() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(20.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            HeadlineH4(
                text = stringResource(id = R.string.not_internet),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500,
            )
        }
    }


}