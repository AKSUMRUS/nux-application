package com.ledokol.thebestprojectever.ui.components.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimary
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonSecondary
import org.checkerframework.common.subtyping.qual.Bottom

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StartRegistrationScreen(
    navController: NavController,
){

    fun onClickSignUp(): Unit{
        navController.navigate("signup_screen") {
            popUpTo("signup_screen")
            launchSingleTop = true
        }
    }

    fun onClickLogin(){
        navController.navigate("login_screen") {
            popUpTo("login_screen")
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
    ){
        Box(
            modifier = Modifier
                .weight(5f)
            ,

        ){

            val pagerState = rememberPagerState(pageCount = 3)

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ){
                page ->
                when(page) {
                    0 ->
                        HeadlineH4(
                            text = stringResource(id = R.string.hello_registration),
                            modifier = Modifier
                                .padding(bottom = 70.dp)
                                .align(BottomCenter)
                            ,
                            fontWeight = FontWeight.W700,
                        )
                    1 ->
                        HeadlineH4(
                            text = stringResource(id = R.string.hello_registration),
                            modifier = Modifier
                                .padding(bottom = 70.dp)
                                .align(BottomCenter)
                            ,
                            fontWeight = FontWeight.W700,
                        )
                    2 ->
                        HeadlineH4(
                            text = stringResource(id = R.string.hello_registration),
                            modifier = Modifier
                                .padding(bottom = 70.dp)
                                .align(BottomCenter)
                            ,
                            fontWeight = FontWeight.W700,
                        )

                }
            }
        }


        Column(
            modifier = Modifier
                .weight(2f)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
//            verticalArrangement = Arrangement.Bottom,
        ){
            ButtonPrimary(
                text = stringResource(id = R.string.sign_up),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
//                .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                ,
                onClick = {onClickSignUp()},
                )
                ButtonSecondary(
                text = stringResource(id = R.string.login),
                modifier = Modifier.fillMaxWidth(),
                onClick = {onClickLogin()},
            )
        }
    }
}