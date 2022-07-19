package com.ledokol.thebestprojectever.ui.components.molecules.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH2
import org.intellij.lang.annotations.JdkConstants

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
            contentDescription = "Avatar"
        )

        HeadlineH2(text = state!!.nickname)

    }

}