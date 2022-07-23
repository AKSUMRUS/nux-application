package com.ledokol.thebestprojectever.ui.components.molecules

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomEnd
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
import androidx.core.app.ActivityCompat
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class URIPathHelper {

    fun getPath(context: Context, uri: Uri): String? {
        val isKitKatorAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

// DocumentProvider
        if (isKitKatorAbove && DocumentsContract.isDocumentUri(context, uri)) {
// ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                    return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.getContentResolver().query(uri!!, projection, selection, selectionArgs,null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
}

@Composable
fun UserInformationProfile(
    name: String,
    profile: Boolean,
    onClickEdit: () -> Unit,
    profileViewModel: ProfileViewModel,
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

        if(imageUri!=null){
            val uriPathHelper = URIPathHelper()
            val filePath = uriPathHelper.getPath(context, imageUri!!)
            Log.i("FilePath", filePath.toString())
            val file = File(filePath)
            val requestFile: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val multiPartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

//            val imageStream: InputStream = context.contentResolver.openInputStream(imageUri!!)!!
//            val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream)
//            var encodedImage: String? = encodeImage(selectedImage)
//
            profileViewModel.onEvent(ProfileEvent.UpdateAvatar(
                accessToken = profileViewModel.state.profile!!.access_token,
                profile_pic = multiPartBody
            )
            )
        }
    }

//    val REQUEST_CODE = 200
//
//    var PERMISSION_ALL = 1
//
//// we require two main permissions for accessing camera and external storage. We will use this as runtime permission.
//
//    var PERMISSIONS = arrayOf(
//        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//        Manifest.permission.CAMERA
//    )
//
//    var selectedImageUri: Uri? = null
//    var cameraUri: Uri? = null
//
//    if (!hasPermissions(this, *PERMISSIONS)) {
//        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//    }
//
//    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
//        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
//    }


    val top: Dp = if (!profile) 70.dp else 120.dp
    bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous)

    LaunchedEffect(imageUri){
        if(imageUri!=null){
            Log.e("updateImage", "UPDATE!!! $imageUri")
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,imageUri).asImageBitmap()

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, imageUri!!)
                bitmap = ImageDecoder.decodeBitmap(source).asImageBitmap()
            }
        }
    }

    Box(){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = top, bottom = 10.dp)
        ){
            Column(
                modifier = Modifier
                    .weight(4f),
            ){
                if(profile){
                    Body1(
                        text = stringResource(id = R.string.good_evening),
                        color = MaterialTheme.colors.onSecondary,
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
                    .weight(2f),
            ){

                Box(
                    modifier = Modifier
                        .padding(end = 20.dp, bottom = 20.dp)
                        .clickable {
                            Toast
                                .makeText(context, "fddffd", Toast.LENGTH_LONG)
                                .show()
                            launcher.launch("image/*")
                        }

                ){
                    Image(
                        bitmap = bitmap!!,
                        contentDescription = "Аноним",
                        modifier = Modifier
                            .size(100.dp),
//                            .padding(end = 20.dp, bottom = 20.dp)
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }

        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.edit),
            contentDescription = null,
            modifier = Modifier
                .align(BottomEnd)
                .padding(end = 20.dp, bottom = 10.dp)
                .clickable {
                    onClickEdit()
                }
                .size(50.dp)
                .padding(15.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

private fun encodeImage(bm: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}