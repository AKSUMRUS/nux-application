package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.Profile
//import com.ledokol.thebestprojectever.data.remote.Common
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.domain.ProfileJSON
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithIcon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SignUpScreenSecond(
    viewModel: MainViewModel,
    navController: NavController
){
//    val retrofitServices: RetrofitServices = Common.retrofitService
    val (nickname,setNickname) = remember{ mutableStateOf("") }
    val (password,setPassword) = remember{ mutableStateOf("") }
    val (checkPrivacy,setCheckPrivacy) = remember{ mutableStateOf(false) }

    val buttonClick = {
        navController.navigate("signup_screen_second") {
            popUpTo("signup_screen_second")
            launchSingleTop = true
        }
    }

    fun checkboxChanged(newCheck: Boolean){
        setCheckPrivacy(newCheck)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Column(
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))
        ){
            HeadlineH2(
                text = stringResource(R.string.sign_up),
                fontWeight = FontWeight.W700,
            )
            HeadlineH6(
                text = stringResource(R.string.description_signup_first),
                fontWeight = FontWeight.W700,
                color = MaterialTheme.colors.onBackground,
            )
        }

        TextFieldTrailingIcon(
            text = nickname,
            placeholder = stringResource(id = R.string.profile_nickname),
            onValueChange = setNickname,
            buttonClick = {
                setNickname("")
            },
            icon = Icons.Default.Close,
        )

        TextFieldTrailingIcon(
            text = password,
            placeholder = stringResource(id = R.string.profile_name),
            onValueChange = setPassword,
            buttonClick = {
                setPassword("")
            },
            icon = Icons.Default.Close,
        )


//        Button(text = stringResource(R.string.sign_up), onClick = {
//            val query: ProfileJSON = ProfileJSON(nickname = nickname,password = password)
//            Log.e("Tock",query.toString())
//            val profileCall : Call<Profile> = retrofitServices.createProfile(
//                query
//            )
//            profileCall.enqueue(object : Callback<Profile> {
//                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
//                    if (response.isSuccessful) {
//                        Log.e("ERRtR",response.body().toString())
//                        viewModel.insertProfile(Profile(access_token = response.body()?.access_token.toString(),nickname = nickname))
//                        navController.navigate("quick_game") {
//                            popUpTo("quick_game")
//                            launchSingleTop = true
//                        }
//                    }
//                    else{
//                        Log.e("Err",response.code().toString())
//                    }
//                }
//
//                override fun onFailure(call: Call<Profile>, t: Throwable) {
//                    Log.e("ERRR",t.toString())
//                }
//            })
//        })

        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .weight(4f)
            ){
                com.ledokol.thebestprojectever.ui.components.atoms.Checkbox(
                    value = checkPrivacy,
                    onChange = {checkboxChanged(it)},
                    modifier = Modifier.padding(start = 0.dp),
                )
                Body1(
                    text = stringResource(id = R.string.privacy_police),
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 0.dp)
                    .weight(1f)
                ,
                horizontalArrangement = Arrangement.End,
            ){
                ButtonWithIcon(
                    icon = Icons.Default.ArrowForward,
                    onClick = buttonClick,
                    modifier = Modifier.background(MaterialTheme.colors.primary),
                )
            }
        }
    }
}