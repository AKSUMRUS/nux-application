package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.Search
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonBorder
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.friend.AddFriendByNickname
import com.ledokol.thebestprojectever.ui.components.molecules.friend.AddFriendByPhone

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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxSize()
                .align(Alignment.Center)
        ){
            AddFriendByNickname(
                nickname = nickname,
                onClickButton = {
                    Log.e("addFriend","startFront")
                    userViewModel.onEvent(UserEvent.AddFriend(nickname = nickname, access_token = profileViewModel.state.profile!!.access_token))
                    nickname = ""
                    Toast.makeText(context, "Запрос отправлен!", Toast.LENGTH_LONG).show()
                },
                onValueChange = {
                    nickname = it
                }
            )

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

            ShareInvite()
        }
    }
}