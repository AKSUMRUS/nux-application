package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryLeadingIcon
import com.ledokol.thebestprojectever.ui.components.screens.profile.getIcon
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar

@Composable
fun ShareScreen(
    navController: NavController,
    fromContacts: Boolean = false,
){

    val context: Context = LocalContext.current

    val socialAppsBase = mutableListOf(
        "com.vkontakte.android",
        "org.telegram.messenger",
        "com.whatsapp",
        "com.instagram.android",
        "com.snapchat.android",
        "com.facebook.katana"
    )
    val socialApps = remember{ mutableStateListOf<String>()}

    LaunchedEffect(key1 = true, block = {
        socialApps.clear()
        for (app in socialAppsBase){
            if(appInstalledOrNot(context,app)){
                socialApps.add(app)
//                socialApps.remove(app)
            }
        }
    })

    fun onClick(app: String?){
        val intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "Скачай это приложение — &#010;https://vk.com")
//        intent.putExtra(Intent.EXTRA_TEXT, stringResource(id = R.string.repost_text_in_app).toString())
        intent.type="text/plain"

        if(app==null){
            context.startActivity(Intent.createChooser(intent,"Поделиться"))
        }else{
            intent.setPackage(app)
            context.startActivity(intent)
        }

        if(fromContacts){
            navController.popBackStack()
        }
    }

    fun buttonBackClick(){
        navController.popBackStack()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        BackToolbar (
            buttonBackClick = {
                buttonBackClick()
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            content = {
                item(
                    span = { GridItemSpan(3) }
                ) {
                    HeadlineH5(
                        text = "Поделиться",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W700,
                    )
                }
                items(socialApps) { app ->
                    if (appInstalledOrNot(context,app)) {
                        Icon(
                            getIcon(context, context.packageManager, app)!!.asImageBitmap(),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
//                            .size(50.dp)
                                .clickable { onClick(app) }
                                .padding(15.dp)
                        )
                    }
                }

                item(
                    span = { GridItemSpan(3) }
                ) {
                    ButtonPrimaryLeadingIcon(
                        text = stringResource(id = R.string.repost_another_app),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp, top = 10.dp, bottom = 10.dp),
                        onClick = { onClick(null) },
                        icon = Icons.Default.Share,
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp,bottom = 100.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
        )
    }

}

fun appInstalledOrNot(context: Context, str: String?): Boolean {
    return try {
        context.packageManager.getPackageInfo(str!!, PackageManager.GET_ACTIVITIES)
        true
    } catch (unused: PackageManager.NameNotFoundException) {
        false
    }
}