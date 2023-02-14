package com.nux.dvor.ui.components.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.R
import com.nux.studio.dvor.core_ui.atoms.buttons.ButtonFull
import com.nux.dvor.ui.components.atoms.textfields.OtpTextField
import com.nux.studio.dvor.core_ui.atoms.texts.Body1
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH4
import com.nux.studio.dvor.ui.components.molecules.BackToolbar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreenVerifyPhone(
    phoneCode: String,
    phone: String,
    setPhoneCode: (String) -> Unit,
    buttonNextClick: () -> Unit,
    buttonBackClick: () -> Unit,
    error: String
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    if (error.isEmpty()) {
        LaunchedEffect(focusRequester) {
            focusRequester.requestFocus()
            keyboard?.show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackToolbar(
            buttonBackClick = {
                buttonBackClick()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 50.dp, end = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (error.isEmpty()) {
                HeadlineH4(
                    text = stringResource(id = R.string.verify_code),
                    fontWeight = FontWeight.W700,
                    color = MaterialTheme.colors.onBackground
                )
                Body1(
                    text = stringResource(id = R.string.verify_code_description, phone),
                    color = MaterialTheme.colors.onPrimary,
                )

                OtpTextField(
                    otpText = phoneCode,
                    onOtpTextChange = { value, otpInputFilled ->
                        setPhoneCode(value)
                    },
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .focusRequester(focusRequester),
                )

                ButtonFull(
                    text = stringResource(id = R.string.confirm),
                    colorText = MaterialTheme.colors.onBackground,
                    colorBackground = MaterialTheme.colors.secondary,
                    onClick = { buttonNextClick() },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )
            } else {
                HeadlineH4(
                    text = error,
                    color = MaterialTheme.colors.error,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier
                        .focusRequester(focusRequester),
                )
            }
        }
    }


}