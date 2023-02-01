package com.ledokol.thebestproject.ui.components.screens.registration

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.ui.components.screens.registration.signup.SignUpScreenPhone

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {
    var phone by remember { mutableStateOf("") }
    var phoneCode by remember { mutableStateOf("") }
    var numberScreen by remember { mutableStateOf(0) }
    var checkPrivacy by remember { mutableStateOf(false) }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(true) {
        keyboard?.show()
    }

    val buttonNextClick = {
        numberScreen++
    }

    fun buttonBackClick() {
        numberScreen--
    }

    when (numberScreen) {
        0 -> {
            fun buttonClickNext() {
                profileViewModel.onEvent(
                    ProfileEvent.ConfirmationPhone(
                        phone = "+7$phone",
                        reason = "login"
                    )
                )
                numberScreen++
            }


            SignUpScreenPhone(
                phone = phone,
                setPhone = {
                    if (it.length <= 10) {
                        phone = it
                    }
                },
                buttonBackClick = { navController.popBackStack() },
                buttonNextClick = {
                    buttonClickNext()
                },
            )
        }
        1 -> {
            fun buttonClickNext() {
                profileViewModel.onEvent(
                    ProfileEvent.Login(
                        phone = "+7$phone",
                        id = profileViewModel.state.id_confirmation_phone,
                        code = phoneCode,
                    )
                )
            }

            SignUpScreenVerifyPhone(
                phoneCode = phoneCode,
                phone = "+7$phone",
                setPhoneCode = { phoneCode = it },
                buttonBackClick = { buttonBackClick() },
                buttonNextClick = { buttonClickNext() },
                error = profileViewModel.state.verifyErrorMessage
            )
        }
    }
}