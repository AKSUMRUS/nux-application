package com.ledokol.thebestprojectever.ui.components.molecules.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import org.intellij.lang.annotations.JdkConstants
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH2
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH3

@Composable
fun EditProfileTopBlock(
    profileViewModel: ProfileViewModel
) {

    val state = profileViewModel.state.profile

    Column(
         modifier = Modifier
             .fillMaxWidth()
             .padding(bottom = 20.dp)
        ,
        horizontalAlignment = CenterHorizontally
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
            contentDescription = "Avatar",
            modifier = Modifier.size(130.dp)
        )

        HeadlineH3(
            text = state!!.nickname,
            fontWeight = W700
        )
        Body1(
            text = "89618498101",
            fontWeight = W700,
            color = MaterialTheme.colors.onSecondary
        )

    }

}