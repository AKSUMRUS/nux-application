package com.nux.studio.dvor.ui.components.molecules.friend

//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nux.studio.dvor.ui.components.atoms.Cross
import com.nux.studio.dvor.ui.components.atoms.texts.Body1
import com.nux.studio.dvor.ui.components.atoms.texts.HeadlineH6
import com.nux.studio.dvor.R

@Composable
fun PreviewEmptyFriend(
    navController: NavController,
) {

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 40.dp, end = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Icon(
                ImageBitmap.imageResource(id = R.drawable.smile),
                contentDescription = null,
                modifier = Modifier.size(80.dp, 80.dp),
                tint = MaterialTheme.colors.onPrimary,
            )

            HeadlineH6(
                text = stringResource(id = R.string.empty_friend_title),
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Center,
            )

            Body1(
                text = stringResource(id = R.string.empty_friend_description),
                modifier = Modifier
                    .padding(),
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center,

                )
        }

        Cross(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )

    }
}