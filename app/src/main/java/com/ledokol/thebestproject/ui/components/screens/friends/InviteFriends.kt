package com.ledokol.thebestproject.ui.components.screens.friends

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
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
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestproject.ui.components.molecules.friend.BoxTypeAddFriend
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.ledokol.thebestproject.ui.components.screens.ShareInvite
import com.ledokol.thebestproject.ui.navigation.ScreenRoutes

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
//        BackToolbar (
//            buttonBackClick = {
//                navController.popBackStack()
//            }
//        )

        Column(
            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 0.dp)
                .fillMaxSize()
//                .align(Alignment.Start)
        ){
            HeadlineH4(
                text = stringResource(id = R.string.add_friend_title),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 10.dp),
                fontWeight = W600,
            )

            BoxTypeAddFriend(
                icon = ImageBitmap.imageResource(id = R.drawable.add_by_nickname),
                title = stringResource(id = R.string.add_by_nickname),
                onClick = {
                    navController.navigate(ScreenRoutes.ADD_BY_NICKNAME){
                        popUpTo(ScreenRoutes.ADD_BY_NICKNAME)
                        launchSingleTop = true
                    }
                }
            )

            BoxTypeAddFriend(
                icon = ImageBitmap.imageResource(id = R.drawable.add_from_contacts),
                title = stringResource(id = R.string.add_from_contacts),
                onClick = {
                    navController.navigate(ScreenRoutes.CONTACTS_LIST){
                        popUpTo(ScreenRoutes.CONTACTS_LIST)
                        launchSingleTop = true
                    }
                }
            )

            BoxTypeAddFriend(
                icon = ImageBitmap.imageResource(id = R.drawable.add_by_qr),
                title = stringResource(id = R.string.add_by_qr),
                onClick = {
                    navController.navigate(ScreenRoutes.QR_CODE_PROFILE){
                        popUpTo(ScreenRoutes.QR_CODE_PROFILE)
                        launchSingleTop = true
                    }
                }

            )


            ShareInvite(
                profile_id = profileViewModel.state.profile!!.id
            )
        }
    }
}

fun generateQR(content: String?, size: Int): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val barcodeEncoder = BarcodeEncoder()
        bitmap = barcodeEncoder.encodeBitmap(
            content,
            BarcodeFormat.QR_CODE, size, size
        )
    } catch (e: WriterException) {
        Log.e("generateQR()", e.message.toString())
    }
    return bitmap
}