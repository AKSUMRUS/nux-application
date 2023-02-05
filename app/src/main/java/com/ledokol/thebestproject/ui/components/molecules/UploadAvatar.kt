package com.ledokol.thebestproject.ui.components.molecules

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import id.zelory.compressor.calculateInSampleSize

@Composable
fun UploadAvatar(
    profilePic: String,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri

        if (imageUri != null) {
            val options = BitmapFactory.Options()
            options.inSampleSize = calculateInSampleSize(options, 10, 10)
            options.inJustDecodeBounds = false

            Log.e("uploadAvatar", imageUri.toString())

            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                Uri.parse(imageUri.toString()),
            )

            profileViewModel.onEvent(
                ProfileEvent.UpdateAvatar(
                    profile_pic = bitmap
                )
            )
            userViewModel.onEvent(UserEvent.OpenScreen(screen = "profile"))
        }
    }

    if (profileViewModel.state.isLoading) {
        Box(
            modifier = Modifier.padding(5.dp)
        ) {
            LoadingViewCenter()
        }
    } else {
        AsyncImage(
            model = profilePic,
            contentDescription = "Аноним",
            modifier = Modifier
                .then(modifier)
                .clickable {
                    launcher.launch("image/*")
                },
            contentScale = ContentScale.Crop,
        )
    }
}