package com.nux.studio.dvor.ui.components.molecules.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.ui.components.atoms.buttons.ButtonFull
import com.nux.studio.dvor.ui.components.atoms.texts.Body1
import com.nux.studio.dvor.ui.components.atoms.texts.HeadlineH4
import com.nux.studio.dvor.ui.components.atoms.texts.HeadlineH6
import com.nux.studio.dvor.R

@Composable
fun RequestReadContacts(
    onClickButton: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 100.dp, bottom = 100.dp, start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.weight(3f)
        ) {
            HeadlineH4(
                text = stringResource(id = R.string.hello_permission_contacts),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.W500,
            )
            HeadlineH6(
                text = stringResource(id = R.string.need_permission_contacts),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                fontWeight = FontWeight.W500,
            )

//            HeadlineH4(
//                text = stringResource(id = R.string.reason_permission_contacts),
//                color = MaterialTheme.colors.onBackground,
//                modifier = Modifier
//                    .fillMaxWidth(),
//                fontWeight = FontWeight.W500,
//            )
        }

        Column(
            modifier = Modifier.weight(3f),
            verticalArrangement = Arrangement.Bottom
        ) {
            Body1(
                text = stringResource(id = R.string.explain_permission_contacts),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                fontWeight = FontWeight.W500,
            )

            ButtonFull(
                text = stringResource(id = R.string.allow_permission_contacts),
                colorText = MaterialTheme.colors.onBackground,
                colorBackground = MaterialTheme.colors.secondary,
                onClick = { onClickButton() },
            )
        }
    }
}
