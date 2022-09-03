package com.ledokol.thebestproject.ui.components.molecules

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.LoadingView
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4
import id.zelory.compressor.calculateInSampleSize

@Composable
fun UploadAvatar(
    profile_pic: String,
    profileViewModel: ProfileViewModel,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri

        if(imageUri!=null){
            val options = BitmapFactory.Options()
            options.inSampleSize = calculateInSampleSize(options, 10,10);
            options.inJustDecodeBounds = false;

            Log.e("uploadAvatar", imageUri.toString())

            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                context.getContentResolver(),
                Uri.parse(imageUri.toString()),
            )

            profileViewModel.onEvent(
                ProfileEvent.UpdateAvatar(
                accessToken = profileViewModel.state.profile!!.access_token,
                profile_pic = bitmap
            )
            )
            userViewModel.onEvent(UserEvent.OpenScreen(screen = "profile"))
        }
    }

    if(profileViewModel.state.isLoading){
        LoadingView()
    }else{
        AsyncImage(
            model = profile_pic,
            contentDescription = "Аноним",
            modifier = Modifier
                .then(modifier)
                .clickable {
                    launcher.launch("image/*")
                }
            ,
            contentScale = ContentScale.Crop,
        )
    }
}