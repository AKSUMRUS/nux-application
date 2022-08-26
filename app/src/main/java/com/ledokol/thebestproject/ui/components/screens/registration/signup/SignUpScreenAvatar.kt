package com.ledokol.thebestproject.ui.components.screens.registration

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestproject.domain.profile.DefaultProfilePic
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.texts.Body2
import com.ledokol.thebestproject.ui.components.molecules.BackToolbar
import com.ledokol.thebestproject.ui.components.molecules.TitleRegistration

import com.ledokol.thebestproject.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreenAvatar(
    avatars: List<DefaultProfilePic>,
    avatarSelected: String,
    setAvatar: (String) -> Unit,
    setAvatarId: (String) -> Unit,
    buttonNextClick: () -> Unit,
    buttonBackClick: () -> Unit,
    error: String = "",
){
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TitleRegistration(
                title = stringResource(R.string.choose_avatar),
                description = "",
            )

            LazyVerticalGrid(columns = GridCells.Fixed(3), content = {
                items(avatars){ avatar ->
                    val modifier = if(avatar.url == avatarSelected) Modifier.border(3.dp, MaterialTheme.colors.primary) else Modifier.border(1.dp, MaterialTheme.colors.secondaryVariant)
                    AsyncImage(
                        model = avatar.url,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(height = 100.dp, width = 100.dp)
                            .clickable {
                                setAvatar(avatar.url)
                                setAvatarId(avatar.id)
                            }
                            .then(modifier)
                        ,
                        contentScale = ContentScale.Crop,
                    )
                }
            })

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
            onClick = buttonNextClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                .fillMaxWidth()
            ,
        )
    }
}