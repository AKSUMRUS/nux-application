
package com.ledokol.thebestprojectever.ui.components.molecules.contacts

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.ContactViewModel
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.atoms.LoadingView
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestprojectever.ui.components.screens.getContactArray


@OptIn(ExperimentalPermissionsApi::class)
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
    ){
        Column(
            modifier = Modifier.weight(3f)
        ){
            HeadlineH4(
                text = stringResource(id = R.string.hello_permission_contacts),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.W500,
            )
            HeadlineH5(
                text = stringResource(id = R.string.need_permission_contacts),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.W500,
            )

            HeadlineH4(
                text = stringResource(id = R.string.reason_permission_contacts),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.W500,
            )
        }

        Column(
            modifier = Modifier.weight(3f),
            verticalArrangement = Arrangement.Bottom
        ){
            Body1(
                text = stringResource(id = R.string.explain_permission_contacts),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                fontWeight = FontWeight.W500,
            )

            ButtonFull(
                text = stringResource(id = R.string.allow_permission_contacts),
                onClick = { onClickButton() },
            )
        }
    }
}
