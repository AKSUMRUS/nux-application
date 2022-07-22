package com.ledokol.thebestprojectever.ui.components.molecules

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH4

@Composable
fun UserInformationProfile(
    name: String,
    profile: Boolean,
){
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    var bitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }


    val top: Dp = if (!profile) 70.dp else 120.dp

    bitmap = if(imageUri!=null){
        if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver,imageUri).asImageBitmap()

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageUri!!)
            ImageDecoder.decodeBitmap(source).asImageBitmap()
        }
    }else{
        ImageBitmap.imageResource(id = R.drawable.anonymous)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = top, bottom = 10.dp)
    ){
        Column(
            modifier = Modifier
                .weight(2f),
        ){
            if(profile){
                Body1(
                    text = stringResource(id = R.string.good_evening),
                    color = MaterialTheme.colors.onBackground,
                )
            }
            HeadlineH4(
                text = name,
                fontWeight = FontWeight.W700,
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
                .weight(1f)
            ,
        ){

            Box(
                modifier = Modifier.clickable {
                    Toast.makeText(context,"fddffd",Toast.LENGTH_LONG).show()
                    launcher.launch("image/*")
                }

            ){
                Image(
                    bitmap = bitmap!!,
                    contentDescription = "Аноним",
                    modifier = Modifier
                        .size(height = 120.dp, width = 120.dp)
//                        .align(CenterVertically)
                    ,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}