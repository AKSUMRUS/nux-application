package com.nux.dvor.ui.components.screens.friends

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.nux.dvor.ui.components.atoms.buttons.ButtonPrimaryLeadingIcon
import com.nux.studio.dvor.R
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH4
import com.nux.studio.dvor.data.local.user.UserEvent
import com.nux.studio.dvor.presentation.ProfileViewModel
import com.nux.studio.dvor.presentation.UserViewModel
import com.nux.studio.dvor.ui.components.molecules.LoadingViewCenter
import com.nux.studio.dvor.ui.components.molecules.friend.AddFriendInSearch
import com.nux.studio.dvor.ui.components.molecules.friend.BoxTypeAddFriend
import com.nux.studio.dvor.ui.components.molecules.friend.TitleFriends
import com.nux.studio.dvor.ui.components.screens.profile.getIcon
import com.nux.studio.dvor.ui.components.screens.profile.getLinkProfile
import com.nux.studio.dvor.ui.navigation.ScreenRoutes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InviteFriend(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
) {
    val context = LocalContext.current
    var nickname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val state = userViewModel.state
    val friends = state.recommendedFriends

    val socialAppsBase = mutableListOf(
        "com.vkontakte.android",
        "org.telegram.messenger",
        "com.whatsapp",
        "com.instagram.android",
        "com.snapchat.android",
        "com.facebook.katana"
    )
    val socialApps = remember { mutableStateListOf<String>() }

    LaunchedEffect(key1 = true, block = {
        socialApps.clear()
        for (app in socialAppsBase) {
            if (appInstalledOrNot(context, app)) {
                socialApps.add(app)
            }
        }
    })
    fun onClick(app: String? = null) {
        val dynamicLinkUri = getLinkProfile(profile_id = profileViewModel.state.profile!!.id)
        Log.e("dynamicLinkUri", dynamicLinkUri)

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Добавляй меня в друзья в Dvor $dynamicLinkUri"
        )
        intent.type = "text/plain"

        if (app == null) {
            context.startActivity(Intent.createChooser(intent, "Поделиться"))
        } else {
            intent.setPackage(app)
            context.startActivity(intent)
        }
    }

    Log.e("RecommendedFriends", friends?.size.toString())

    LaunchedEffect(friends) {
        userViewModel.onEvent(UserEvent.GetRecommendedFriends)
    }

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 40.dp)
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection())
    ) {
        item {
            HeadlineH4(
                text = stringResource(id = R.string.add_friend_title),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 10.dp),
                fontWeight = W600,
            )
        }
        item {

            BoxTypeAddFriend(
                icon = ImageBitmap.imageResource(id = R.drawable.add_by_nickname),
                title = stringResource(id = R.string.add_by_nickname),
                onClick = {
                    navController.navigate(ScreenRoutes.ADD_BY_NICKNAME) {
                        popUpTo(ScreenRoutes.ADD_BY_NICKNAME)
                        launchSingleTop = true
                    }
                }
            )
        }

        item {
            BoxTypeAddFriend(
                icon = ImageBitmap.imageResource(id = R.drawable.add_from_contacts),
                title = stringResource(id = R.string.add_from_contacts),
                onClick = {
                    navController.navigate(ScreenRoutes.CONTACTS_LIST) {
                        popUpTo(ScreenRoutes.CONTACTS_LIST)
                        launchSingleTop = true
                    }
                }
            )
        }

        item {
            BoxTypeAddFriend(
                icon = ImageBitmap.imageResource(id = R.drawable.add_by_qr),
                title = stringResource(id = R.string.add_by_qr),
                onClick = {
                    navController.navigate(ScreenRoutes.QR_CODE_PROFILE) {
                        popUpTo(ScreenRoutes.QR_CODE_PROFILE)
                        launchSingleTop = true
                    }
                }
            )
        }

        item {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                socialApps.forEach { app ->
                    if (appInstalledOrNot(context, app)) {
                        Icon(
                            getIcon(context, context.packageManager, app).asImageBitmap(),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .clickable { onClick(app) }
                                .padding(15.dp)
                        )
                    }
                }
            }
        }

        item {
            ButtonPrimaryLeadingIcon(
                text = stringResource(id = R.string.repost_another_app),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                onClick = { onClick() },
                icon = Icons.Default.Share,
            )
        }

        if (state.isLoading) {
            item {
                LoadingViewCenter()
            }
        } else if (friends != null) {
            item {
                TitleFriends(
                    text = stringResource(id = R.string.recommended_friends),
                    modifier = Modifier.padding(top = 0.dp, bottom = 10.dp)
                )
            }
            items(friends.size) { id ->
                val friend = friends[id]
                AddFriendInSearch(
                    user = friend,
                    addFriend = {
                        userViewModel.onEvent(UserEvent.AddFriend(friend.id))
                        Toast.makeText(
                            context,
                            context.getString(R.string.invite_sent),
                            Toast.LENGTH_SHORT
                        ).show()
                        friends.remove(friend)
                    },
                    openFriend = {

                    })
            }
        }
    }
}

private fun generateQR(content: String?, size: Int): Bitmap? {
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

private fun appInstalledOrNot(context: Context, str: String?): Boolean {
    return try {
        context.packageManager.getPackageInfo(str!!, PackageManager.GET_ACTIVITIES)
        true
    } catch (unused: PackageManager.NameNotFoundException) {
        false
    }
}