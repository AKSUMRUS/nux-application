package com.ledokol.thebestproject.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4

@Composable
fun TitleRegistration(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp),
    ) {
        HeadlineH4(
            text = title,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Body1(
            text = description,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }

}