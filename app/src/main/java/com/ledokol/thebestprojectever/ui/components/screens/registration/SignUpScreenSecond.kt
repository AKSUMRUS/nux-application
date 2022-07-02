package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.Profile
//import com.ledokol.thebestprojectever.data.remote.Common
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.domain.ProfileJSON
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithIcon
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.TitleRegistration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SignUpScreenSecond(
    nickname: String,
    setNickname: (String) -> Unit,
    name: String,
    setName: (String) -> Unit,
    buttonNextClick: () -> Unit,
    buttonBackClick: () -> Unit,
    checkPrivacy: Boolean,
    setCheckPrivacy: (Boolean) -> Unit,
){

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        BackToolbar(buttonBackClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {

            TitleRegistration(
                title = stringResource(R.string.sign_up),
                description = stringResource(R.string.description_signup_first),
            )

            TextFieldTrailingImage(
                textCaption = stringResource(id = R.string.choose_nickname),
                text = nickname,
                placeholder = stringResource(id = R.string.profile_nickname),
                onValueChange = setNickname,
                buttonClick = {
                    setNickname("")
                },
                image = ImageBitmap.imageResource(id = R.drawable.cross),
            )

            TextFieldTrailingImage(
                textCaption = stringResource(id = R.string.choose_name),
                text = name,
                placeholder = stringResource(id = R.string.profile_name),
                onValueChange = setName,
                buttonClick = {
                    setName("")
                },
                image = ImageBitmap.imageResource(id = R.drawable.cross),
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.Center,
            ){
                Column(
                    modifier = Modifier
                        .weight(4f)
                ){
                    com.ledokol.thebestprojectever.ui.components.atoms.Checkbox(
                        value = checkPrivacy,
                        onChange = setCheckPrivacy,
                        modifier = Modifier.padding(start = 0.dp),
                    )
                    Body1(
                        text = stringResource(id = R.string.privacy_police),
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .weight(1f)
                    ,
                    horizontalArrangement = Arrangement.End,
                ){
                    ButtonWithIcon(
                        icon = Icons.Default.ArrowForward,
                        onClick = buttonNextClick,
                        modifier = Modifier.background(MaterialTheme.colors.primary),
                    )
                }
            }
        }
    }
}