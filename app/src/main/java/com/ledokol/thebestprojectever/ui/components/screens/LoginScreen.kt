package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.remote.Common
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.Button
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH1
import com.ledokol.thebestprojectever.ui.components.atoms.TextButton
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val retrofitServices: RetrofitServices = Common.retrofitService
    val (nickname,setNickname) = remember{ mutableStateOf("") }
    val (password,setPassword) = remember{ mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeadlineH1(text = stringResource(R.string.login))
        TextField(
            label = stringResource(R.string.nickname),
            text = nickname,
            onValueChange = { setNickname(it) },
        )
        TextField(
            label = stringResource(R.string.password),
            text = password,
            onValueChange = { setPassword(it) },
        )
        Button(text = stringResource(R.string.login), onClick = {
            val profileCall : Call<Profile> = retrofitServices.login(
                nickname = nickname,
                password = password
            )
            profileCall.enqueue(object : Callback<Profile> {
                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    if (response.isSuccessful) {
                        Log.e("ERRtR",response.body().toString())
                        viewModel.insertProfile(Profile(access_token = response.body()!!.access_token,nickname = nickname, password = password))
                        navController.navigate("quick_game") {
                            popUpTo("quick_game")
                            launchSingleTop = true
                        }
                    }
                    else{
                        Log.e("pshel nahui",response.code().toString())
                    }
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    Log.e("ERRR",t.toString())
                }
            })
        })
        TextButton(text = stringResource(R.string.forget_password), onClick = { /*TODO*/ })
        TextButton(text = stringResource(R.string.dont_have_an_account), onClick = {
            navController.navigate("signup_screen") {
                popUpTo("signup_screen")
                launchSingleTop = true
            }
        })
    }
}