package com.ledokol.thebestproject.ui.components.molecules.profile

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH6
import com.ledokol.thebestproject.ui.components.molecules.UploadAvatar
import java.io.ByteArrayOutputStream


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
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
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

    private fun getDataColumn(
        context: Context,
        uri: Uri?,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver
                .query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
}

@Composable
fun UserInformationProfile(
    name: String,
    profile: Boolean,
    profile_pic: String,
    onClickEdit: () -> Unit,
    profileViewModel: ProfileViewModel,
    userViewModel: UserViewModel,
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    var bitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    val state = profileViewModel.state.profile


    val top: Dp = if (!profile) 70.dp else 120.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = top, bottom = 10.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 20.dp)
                    .size(69.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colors.secondary),
            ) {
                UploadAvatar(
                    profile_pic = profile_pic,
                    profileViewModel = profileViewModel,
                    userViewModel = userViewModel
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                HeadlineH5(
                    text = state!!.name,
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.W700,
                )
                HeadlineH6(
                    text = "@${state.nickname}",
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.W700,
                )
            }
        }
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_three_points),
            contentDescription = null,
            modifier = Modifier
                .align(TopEnd)
                .padding(bottom = 10.dp)
                .clickable {
                    onClickEdit()
                }
                .size(40.dp, 40.dp),
            contentScale = ContentScale.Crop,
        )
    }
}


//private fun fromBitmapToFile(){
//    File f = new File(context.getCacheDir(), filename);
//    f.createNewFile();
//
////Convert bitmap to byte array
//    Bitmap bitmap = your bitmap;
//    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//    byte[] bitmapdata = bos.toByteArray();
//
////write the bytes in file
//    FileOutputStream fos = new FileOutputStream(f);
//    fos.write(bitmapdata);
//    fos.flush();
//    fos.close();
//}


private fun encodeImage(bm: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}