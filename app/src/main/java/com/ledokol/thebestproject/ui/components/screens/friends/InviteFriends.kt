package com.ledokol.thebestproject.ui.components.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestproject.ui.components.molecules.BackToolbar
import com.ledokol.thebestproject.ui.components.molecules.friend.AddFriendByNickname
import com.ledokol.thebestproject.ui.components.molecules.friend.BoxTypeAddFriend

@Composable
fun InviteFriend(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
){
    val context = LocalContext.current
    var nickname by remember{ mutableStateOf("")}
    var phone by remember{ mutableStateOf("")}

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        BackToolbar (
            buttonBackClick = {
                navController.popBackStack()
            }
        )

        Column(
            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 50.dp)
                .fillMaxSize()
//                .align(Alignment.Start)
        ){
            HeadlineH4(
                text = stringResource(id = R.string.add_friend_title),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            ,
                fontWeight = W600,
            )

            BoxTypeAddFriend(
                icon = ImageBitmap.imageResource(id = R.drawable.add_by_nickname),
                title = stringResource(id = R.string.add_by_nickname),
                onClick = {
                    navController.navigate("find_friend_by_name"){
                        popUpTo("invite_friends")
                        launchSingleTop = false
                    }
                }
            )

            BoxTypeAddFriend(
                icon = ImageBitmap.imageResource(id = R.drawable.add_from_contacts),
                title = stringResource(id = R.string.add_from_contacts),
                onClick = {  }
            )

            BoxTypeAddFriend(
                icon = ImageBitmap.imageResource(id = R.drawable.add_by_qr),
                title = stringResource(id = R.string.add_by_qr),
                onClick = {}
            )

//            AddFriendByNickname(
//                nickname = nickname,
//                onClickButton = {
//                    Log.e("addFriend","startFront")
//                    userViewModel.onEvent(UserEvent.AddFriend(nickname = nickname, access_token = profileViewModel.state.profile!!.access_token))
//                    nickname = ""
//                    Toast.makeText(context, "Запрос отправлен!", Toast.LENGTH_LONG).show()
//                },
//                onValueChange = {
//                    nickname = it
//                }
//            )

//            AddFriendByPhone(
//                phone = phone,
//                onClickButton = {
//                    userViewModel.onEvent(UserEvent.AddFriend(phone = phone, access_token = profileViewModel.state.profile!!.access_token))
//                    phone = ""
//                    Toast.makeText(context, "Запрос отправлен!", Toast.LENGTH_LONG).show()
//                },
//                onValueChange = {
//                    phone = it
//                },
//            )

            ButtonFull(
                text = "Добавить из контактов",
                onClick = {
                    navController.navigate("contacts_list"){
                        popUpTo("contacts_list")
                        launchSingleTop = true
                    }
                },
                padding = 8.dp,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),

            )

            ShareInvite(
                profile_id = profileViewModel.state.profile!!.id
            )
        }
    }
}