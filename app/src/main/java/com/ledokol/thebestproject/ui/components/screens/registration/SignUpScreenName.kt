package com.ledokol.thebestproject.ui.components.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.textfields.TextField
import com.ledokol.thebestproject.ui.components.atoms.texts.Body2
import com.ledokol.thebestproject.ui.components.molecules.BackToolbar
import com.ledokol.thebestproject.ui.components.molecules.TitleRegistration


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreenName(
    name: String,
    setName: (String) -> Unit,
    buttonNextClick: () -> Unit,
    buttonBackClick: () -> Unit,
    checkPrivacy: Boolean,
    setCheckPrivacy: (Boolean) -> Unit,
    error: String = "",
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        keyboard?.show()
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackToolbar(buttonBackClick)

        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 100.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TitleRegistration(
                title = stringResource(R.string.choose_name),
                description = "",
            )

            TextField(
                text = name,
                placeholder = stringResource(id = R.string.profile_name),
                onValueChange = setName,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = {
                    buttonNextClick()
                }),
                modifier = Modifier
                    .focusRequester(focusRequester),
                capitalization = KeyboardCapitalization.Sentences,
            )

            Body2(
                text = error,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Start),
                color = MaterialTheme.colors.error,
            )
        }

        ButtonFull(
            text = stringResource(id = R.string.next),
            colorText = MaterialTheme.colors.onBackground,
            colorBackground = MaterialTheme.colors.secondary,
            onClick = buttonNextClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                .fillMaxWidth(),
        )
    }
}