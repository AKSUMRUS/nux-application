package com.ledokol.thebestproject.ui.components.screens.registration

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.LoadingView
import com.ledokol.thebestproject.ui.components.screens.registration.signup.SignUpScreenPhone

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    profileViewModel: ProfileViewModel,
    usersViewModel: UserViewModel,
    navController: NavController
){
    var phone by remember { mutableStateOf("") }
    var phoneCode by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var avatar by remember { mutableStateOf("") }
    var avatarId by remember { mutableStateOf("") }
    var correctText by remember{ mutableStateOf(true)}
    var errorName by remember { mutableStateOf("") }
    var errorNickname by remember { mutableStateOf("") }
    var errorPhone by remember { mutableStateOf("") }
    var errorAvatar by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var numberScreen by remember { mutableStateOf(0) }
    var checkPrivacy by remember{ mutableStateOf(false) }
    val keyboard = LocalSoftwareKeyboardController.current

    val state = profileViewModel.state

    LaunchedEffect(usersViewModel.state.existsUser){
        if(usersViewModel.state.existsUser == false){
            errorNickname = ""
            if(numberScreen==1){
                numberScreen++
            }else if(numberScreen==3){
                profileViewModel.onEvent(ProfileEvent.ConfirmationPhone(phone = "+7$phone", reason = "registration"))
                numberScreen++
            }
        }else if(usersViewModel.state.existsUser == true){
            if(numberScreen==1){
                errorNickname = "Такой никнейм уже существует :("
            }else if(numberScreen==3){
                errorPhone = "Такой телефон уже зарегистрирован :("
            }
        }
        usersViewModel.setNullForExistsUser()
    }

    LaunchedEffect(true){
        numberScreen = 0
        keyboard?.show()
    }

    fun buttonBackClick() {
        numberScreen--
    }

    if(usersViewModel.state.isLoading){
        LoadingView()
    }else{
        when(numberScreen) {
            0 -> {
                val buttonClickNext = {
                    var verifyName: Boolean = name.isNotEmpty()

                    if(verifyName){
                        errorName = ""
                        numberScreen++
                    }else{
                        errorName = "Имя не может быть пустым"
                    }
                }


                SignUpScreenName(
                    name = name,
                    setName = {
                        if(it.length<=20){
                            name = it
                        }
                    },
                    buttonNextClick = {buttonClickNext()},
                    buttonBackClick = {navController.popBackStack() },
                    checkPrivacy = checkPrivacy,
                    setCheckPrivacy = {
                        checkPrivacy = it
                    },
                    error = errorName,
                )
            }
            1 -> {
                fun checkCorrectNickname(nickname: String): Boolean{
                    var verifyNickname: Boolean = (nickname.isNotEmpty())

                    for(symbol in nickname){
                        if(symbol=='_' || symbol in '0'..'9') continue
                        if(!((symbol in 'a'..'z')||(symbol in 'A'..'Z'))){
                            verifyNickname = false
                            break
                        }
                    }

                    return verifyNickname
                }

                val buttonClickNext = {
                    val verifyNickname: Boolean = checkCorrectNickname(nickname)

                    if(verifyNickname){
                        usersViewModel.onEvent(UserEvent.CheckExistsNickname(nickname = nickname))
                        profileViewModel.onEvent(ProfileEvent.GetDefaultProfilePics)
                    }else{
                        errorNickname = "Неккоректный никнейм"
                    }
                }

                SignUpScreenNickname(
                    nickname = nickname,
                    setNickname = {
                        if(it.length<=12){
                            nickname = it
                            correctText = checkCorrectNickname(nickname)
                            errorNickname = ""
                        }
                    },
                    buttonNextClick = {buttonClickNext()},
                    buttonBackClick = {buttonBackClick() },
                    checkPrivacy = checkPrivacy,
                    setCheckPrivacy = {
                        checkPrivacy = it
                    },
                    error = errorNickname,
                    correctText = correctText,
                )
            }
            2 -> {
                val buttonClickNext = {
                    var verifyAvatar: Boolean = (avatar.isNotEmpty())

                    if(verifyAvatar){
                        numberScreen++
                    }else{
                        errorAvatar = "Выбери свой аватар"
                    }
                }

                SignUpScreenAvatar(
                    avatars = profileViewModel.state.defaultProfilePicsList,
                    avatarSelected = avatar,
                    setAvatar = {
                        avatar = it
                    },
                    setAvatarId = {
                        avatarId = it
                    },
                    error = errorAvatar,
                    buttonBackClick = {buttonBackClick()},
                    buttonNextClick = {buttonClickNext()}
                )
            }
            3 -> {
                fun buttonClickNext(){
                    var verifyPhone: Boolean = phone.length==10

                    if(verifyPhone){
                        usersViewModel.onEvent(UserEvent.CheckExistsPhone(phone = "+7$phone"))
                    }else{
                        errorPhone = "Некорректный номер телефона"
                    }
                }


                SignUpScreenPhone(
                    phone = phone,
                    setPhone = {
                        if(it.length<=10){
                            phone = it
                        }
                    },
                    buttonBackClick = {buttonBackClick()} ,
                    buttonNextClick = {
                        buttonClickNext()
                    },
                    error = errorPhone
                )
            }
            4 -> {
                fun buttonClickNext(){
                    profileViewModel.onEvent(ProfileEvent.SignUp(
                        nickname = nickname,
                        name = name,
                        phone = "+7$phone",
                        default_profile_pic_id = avatarId,
                        id = profileViewModel.state.id_confirmation_phone,
                        code = phoneCode,
                    ))
                }

                SignUpScreenVerifyPhone(
                    phoneCode = phoneCode,
                    phone = "+7$phone",
                    setPhoneCode = {phoneCode = it},
                    buttonBackClick = {buttonBackClick()},
                    buttonNextClick = {buttonClickNext()},
                    error = state.verifyErrorMessage
                )
            }
        }
    }
}