package com.ledokol.thebestprojectever.ui.components.screens

import android.app.ProgressDialog
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
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
import androidx.compose.ui.unit.dp
//import com.amazonaws.auth.AWSCredentials
//import com.amazonaws.auth.AWSStaticCredentialsProvider
//import com.amazonaws.auth.BasicAWSCredentials
//import com.amazonaws.regions.Regions
//import com.amazonaws.services.s3.AmazonS3
//import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1


//private var ivSelectedImage: ImageView? = null
//private var tvStatus: TextView? = null
//private val SELECT_PICTURE = 2
//private var imageUri: Uri? = null
////private var s3uploaderObj: S3Uploader? = null
//private var urlFromS3: String? = null
//private var progressDialog: ProgressDialog? = null
//const val TAG2 = "Test_uploadImage"

@Composable
fun Test() {

//    val credentials: AWSCredentials = BasicAWSCredentials(
//        "YCAJEnZZQb1-KJ2JE08VcqNTN",
//        "YCOPaczys9w2aTgKndaXJugev28uR-kvhPtSiTBN"
//    )
//
//
//    val s3client: AmazonS3 = AmazonS3ClientBuilder
//        .standard()
//        .withCredentials(AWSStaticCredentialsProvider(credentials))
//        .withRegion(Regions.EU_CENTRAL_1)
//        .build()






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
//        uploadImageTos3(context,imageUri!!)
    }



    if(imageUri!=null){
        if (Build.VERSION.SDK_INT < 28) {
            bitmap = MediaStore.Images
                .Media.getBitmap(context.contentResolver,imageUri).asImageBitmap()

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageUri!!)
            bitmap = ImageDecoder.decodeBitmap(source).asImageBitmap()
        }
    }else{
        bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 100.dp, bottom = 10.dp)
    ){
        Column(
            modifier = Modifier
                .weight(2f),
        ){
                Body1(
                    text = stringResource(id = R.string.good_evening),
                    color = MaterialTheme.colors.onBackground,
                )
            HeadlineH4(
                text = "ffffd",
                fontWeight = FontWeight.W700,
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
                .weight(1f),
        ){

            Box(
                modifier = Modifier.clickable {
                    Toast.makeText(context,"fddffd", Toast.LENGTH_LONG).show()
                    launcher.launch("image/*")
                }

            ){
                Image(
                    bitmap = bitmap!!,
                    contentDescription = "Аноним",
                    modifier = Modifier
                        .size(height = 120.dp, width = 120.dp),
//                        .align(CenterVertically)
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

//
//fun initAmazonS3Client(
//    endpoint: String,
//    accessKey: String,
//    secretKey: String
//) =
//    AmazonS3Client(
//        BasicAWSCredentials(accessKey, secretKey)
//    ).apply {
//        setEndpoint(endpoint).apply {
//            println("S3 endpoint is ${endpoint}")
//        }
//        setS3ClientOptions(
//            S3ClientOptions.builder()
//                .setPathStyleAccess(true).build()
//        )
//    }
//
//private fun uploadImageTos3(
//    context: Context,
//    imageUri: Uri,
//) {
////    val path = getFilePathfromURI(imageUri)
//    val path = imageUri.toString()
//    if (path != null) {
//        s3uploaderObj!!.initUpload(path)
//        s3uploaderObj!!.setOns3UploadDone(object : S3Uploader.S3UploadInterface {
//            override fun onUploadSuccess(response: String?) {
//                if (response.equals("Success", ignoreCase = true)) {
//                    urlFromS3 = generates3ShareUrl(context, path)
//                    if (!TextUtils.isEmpty(urlFromS3)) {
//                        Log.e(TAG2, "Uploaded : $urlFromS3")
//                    }
//                }
//            }
//
//            override fun onUploadError(response: String?) {
//                tvStatus!!.text = "Error : $response"
//                Log.e(TAG2, "Error Uploading")
//            }
//        })
//    }
//}

