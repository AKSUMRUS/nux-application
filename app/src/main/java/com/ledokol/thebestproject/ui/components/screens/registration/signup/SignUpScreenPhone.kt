package com.ledokol.thebestproject.ui.components.screens.registration.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonWithIcon
import com.ledokol.thebestproject.ui.components.atoms.textfields.PhoneNumber
import com.ledokol.thebestproject.ui.components.molecules.BackToolbar
import com.ledokol.thebestproject.ui.components.molecules.TitleRegistration


@Composable
fun SignUpScreenPhone(
    phone: String,
    setPhone: (String) -> Unit,
    buttonNextClick: () -> Unit,
    buttonBackClick: () -> Unit,
    error: String = "",
) {
    fun onPhoneChange(text: String) {
        var str = text
        str = str.replace("(", "")
        str = str.replace(")", "")
        str = str.replace("-", "")

        if (str.length > 20) {
            return
        }
        setPhone(str)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackToolbar(buttonBackClick = { buttonBackClick() })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            TitleRegistration(
                title = stringResource(R.string.choose_phone),
                description = "",
            )


            PhoneNumber(
                phone = phone,
                onPhoneChange = { onPhoneChange(it) },
                error = error,
                onNextClick = { buttonNextClick() }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                ButtonWithIcon(
                    icon = Icons.Default.ArrowForward,
                    onClick = { buttonNextClick() },
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.primary),
                )
            }
        }
    }
}