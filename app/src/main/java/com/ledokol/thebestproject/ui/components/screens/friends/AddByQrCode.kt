package com.ledokol.thebestproject.ui.components.molecules

import android.graphics.Bitmap
import android.graphics.Color
import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.ui.components.atoms.Cross
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH6
import com.ledokol.thebestproject.ui.components.screens.profile.getLinkProfile


@Composable
fun AddByQrCode(
    navController: NavController,
    profileViewModel: ProfileViewModel,
) {

    Box{

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){

            HeadlineH5(
                text = stringResource(id = R.string.title_invite_by_qr_code),
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )


            Image(
                generateQR(
                    getLinkProfile(profileViewModel.state.profile!!.id),
                    800),
                contentDescription = "Красный прямоугольник",
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )

            HeadlineH4(
                text = "@${profileViewModel.state.profile!!.nickname}",
                color = MaterialTheme.colors.onPrimary,
                fontWeight = W600,
            )
        }


        Cross(
            onClick = {
                navController.popBackStack()
            }
            ,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}


fun generateQR(content: String?, size: Int): ImageBitmap {
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
    return bitmap!!.asImageBitmap()
}