package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.Alignment
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
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryLeadingIcon

@Composable
fun ShareScreen(
    navController: NavController,
){

    val context: Context = LocalContext.current
    val socialApps = listOf("com.vkontakte.android","com.whatsapp","com.instagram.android", "org.telegram.messenger","com.snapchat.android")

    fun onClick(app: String?){
        val intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
        intent.type="text/plain"

        if(app==null){
            context.startActivity(Intent.createChooser(intent,"Поделиться"))
        }else{
            intent.setPackage(app)
            context.startActivity(intent)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
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
                            .padding(bottom = 20.dp)
                        ,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W700,
                    )
                }
                items(socialApps) { app ->
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

                item(
                    span = {GridItemSpan(3)}
                ){
                    ButtonPrimaryLeadingIcon(
                        text = stringResource(id = R.string.repost_another_app),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp, top = 10.dp, bottom = 10.dp)
                        ,
                        onClick = {onClick(null)},
                        icon = Icons.Default.Share,
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
            ,
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
        )
    }

}