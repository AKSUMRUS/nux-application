package com.ledokol.thebestproject.ui.components.screens.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.textfields.TextField
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.ui.components.atoms.texts.Body2
import com.ledokol.thebestproject.ui.components.molecules.BackToolbar
import com.ledokol.thebestproject.ui.components.molecules.TitleRegistration


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreenNickname(
    nickname: String,
    setNickname: (String) -> Unit,
    buttonNextClick: () -> Unit,
    buttonBackClick: () -> Unit,
    error: String,
    correctText: Boolean,
    checkPrivacy: Boolean,
    setCheckPrivacy: (Boolean) -> Unit,
){
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        keyboard?.show()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        BackToolbar(buttonBackClick)

        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 100.dp)
                .align(Alignment.Center)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            TitleRegistration(
                title = stringResource(R.string.choose_nickname),
                description = stringResource(id = R.string.description_choose_nickname),
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
            ){
                Body1(
                    text = "@",
                    modifier = Modifier
                        .background(MaterialTheme.colors.secondary)
                        .align(CenterVertically)
                        .padding(start = 20.dp,end = 0.dp)
                    ,
                )

                TextField(
                    text = nickname,
                    placeholder = stringResource(id = R.string.profile_nickname),
                    onValueChange = setNickname,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(onNext = {
                        buttonNextClick()
                    }),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .padding(start = 0.dp)
                    ,
                )
            }
            Body2(
                text = stringResource(id = R.string.hint_choose_nickname),
                color = if(correctText) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 10.dp)
            )

            Body2(
                text = error,
                modifier = Modifier.padding(top = 10.dp),
                color = MaterialTheme.colors.error,
            )
        }

        ButtonFull(
            text = stringResource(id = R.string.next),
            onClick = buttonNextClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                .fillMaxWidth()
        )
    }
}