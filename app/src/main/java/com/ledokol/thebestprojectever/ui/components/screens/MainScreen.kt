package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ledokol.thebestprojectever.presentation.user_info.UserViewModel
import com.ledokol.thebestprojectever.ui.navigation.LoginAndSignUp

@Composable
fun MainScreen() {
    val viewModel: UserViewModel = hiltViewModel()
    val state = viewModel.state



    LoginAndSignUp()
}