package com.ledokol.dvor.ui.components.screens.registration

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.dvor.ui.components.atoms.buttons.ButtonRightIcon
import com.ledokol.dvor.ui.components.atoms.texts.Body1
import com.ledokol.dvor.ui.components.atoms.texts.HeadlineH4
import com.ledokol.dvor.ui.navigation.ScreenRoutes
import com.ledokol.dvor.R
@Composable
fun StartRegistrationScreen(
    navController: NavController,
) {
    val context: Context = LocalContext.current

    fun onClickSignUp() {
        navController.navigate(ScreenRoutes.SIGNUP_SCREEN) {
            popUpTo(ScreenRoutes.SIGNUP_SCREEN)
            launchSingleTop = true
        }
    }

    fun onClickLogin() {
        navController.navigate(ScreenRoutes.LOGIN_SCREEN) {
            popUpTo(ScreenRoutes.LOGIN_SCREEN)
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.secondary)
            .fillMaxSize(),
    ) {
        Image(
            ImageVector.vectorResource(id = R.drawable.background_onboarding),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 40.dp)
                .fillMaxWidth()
                .align(TopStart),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(BottomCenter)
                .padding(start = 23.dp, end = 23.dp, bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
//            verticalArrangement = Arrangement.Bottom,
        ) {
            HeadlineH4(
                text = stringResource(id = R.string.title_onboarding),
                modifier = Modifier.padding(bottom = 15.dp),
                color = MaterialTheme.colors.background,
                fontWeight = W500
            )
            Body1(
                text = stringResource(id = R.string.description_onboarding),
                modifier = Modifier.padding(bottom = 30.dp),
                color = MaterialTheme.colors.background
            )

            ButtonRightIcon(
                text = stringResource(id = R.string.start_auth),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
//                .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                ,
                onClick = { onClickSignUp() },
                icon = ImageBitmap.imageResource(id = R.drawable.arrow_right),
                colorText = MaterialTheme.colors.onBackground,
                colorBackground = MaterialTheme.colors.primary
            )
            Body1(
                text = stringResource(id = R.string.i_already_have_an_account),
                modifier = Modifier
                    .drawBehind {
                        val strokeWidth = 2 * density
                        val y = size.height - strokeWidth / 2

                        drawLine(
                            Color.Black,
                            Offset(0f, y + 10),
                            Offset(size.width, y + 10),
                            strokeWidth
                        )
                    }
                    .clickable { onClickLogin() },
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
        }
    }
}