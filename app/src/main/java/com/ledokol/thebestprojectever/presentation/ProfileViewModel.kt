package com.ledokol.thebestprojectever.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.data.local.profile.ProfileState
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.data.repository.ProfileRepository
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val api: RetrofitServices,

): ViewModel() {
    var state by mutableStateOf(ProfileState())

    fun onEvent(event: ProfileEvent){
        when(event){
            is ProfileEvent.UpdateProfileData -> {
                updateProfileData(event.newProfile)
            }
            is ProfileEvent.Login -> {
                login(
                    id = event.id,
                    code = event.code,
                    phone = event.phone,
                )
            }
            is ProfileEvent.UpdateAvatar -> {
                updateAvatar(accessToken = event.accessToken, profile_pic = event.profile_pic)
            }
            is ProfileEvent.GetDefaultProfilePics -> {
                getDefaultProfilePics()
            }
            is ProfileEvent.ConfirmationPhone -> {
                confirmationPhone(phone = event.phone, reason = event.reason)
            }
            is ProfileEvent.GetProfile -> {
                getProfile()
            }
            is ProfileEvent.LogOut -> {
                logOut()
            }
            is ProfileEvent.InviteFriends -> {
                inviteFriends(
                    accessToken = event.accessToken,
                    friends_ids = event.friends_ids,
                    app_id = event.app_id
                )
            }
            is ProfileEvent.SignUp -> {
                signUp(
                    nickname = event.nickname,
                    name = event.name,
                    phone = event.phone,
                    default_profile_pic_id = event.default_profile_pic_id,
                    id = event.id,
                    code = event.code,
                )
            }
            is ProfileEvent.SetCurrentFirebaseToken -> {
                setCurrentFirebaseToken(token = event.token, accessToken = event.accessToken)
            }
            is ProfileEvent.SetFinishRegister -> {
                setFinishRegister(event.accessToken)
            }
            is ProfileEvent.SetDoNotDisturb -> {
                setDoNotDisturb(
                    canDisturb = event.canDisturb,
                    accessToken = event.accessToken
                )
            }
        }
    }

    private fun getDefaultProfilePics() {
        viewModelScope.launch {
            repository.getDefaultProfilePics().collect{
                result ->
                when(result) {
                    is Resource.Success -> {
                        state = state.copy (
                            defaultProfilePicsList = result.data!!.default_profile_pics
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    private fun setDoNotDisturb(
      canDisturb: Boolean,
      accessToken: String
    ) {
        viewModelScope.launch {
            repository.setDoNotDisturb(
                canDisturb = canDisturb,
                accessToken = accessToken
            )
                .collect { result ->
                    when(result){
                        is Resource.Success -> {
                            state = state.copy(
                                profile = result.data
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                        is Resource.Error -> Unit
                    }
                }
        }
    }



    private fun confirmationPhone(
        phone: String,
        reason: String,
    ){
        viewModelScope.launch {
            repository.confirmationPhone(phone = phone, reason = reason).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            id_confirmation_phone = result.data!!.id,
                            verifyErrorMessage = ""
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            verifyErrorMessage = result.message.toString()
                        )
                    }
                }
            }
        }
    }

    private fun updateProfileData(newProfile: Profile){
        viewModelScope.launch {
            repository.updateProfileData(newProfile = newProfile)
        }
    }

    private fun getProfile(){
        viewModelScope.launch {
            val response = repository.getProfile()
            if(response != null) {
                state = state.copy(
                    profile = response,
                    finish_register = response.finishRegister
                )
            } else{
                state = state.copy(
                    profile = response
                )
            }
        }
    }

    private fun updateAvatar(
        accessToken: String,
        profile_pic: Bitmap
    ){
        viewModelScope.launch {
            repository.uploadAvatar(
                accessToken = accessToken,
                profile_pic_bitmap = profile_pic,
            ).collect{ result ->
                when(result){
                    is Resource.Success -> {
                        if(result.data != null) {
                            getMe(accessToken = accessToken)
                        }
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    fun inviteFriends(accessToken: String, friends_ids: List<String>, app_id: String){
        viewModelScope.launch {
            Log.e("INVITE","SENT")
            repository.inviteFriends(
                accessToken = accessToken,
                friends_ids = friends_ids,
                app_id = app_id
            )
        }
    }

    private fun setCurrentFirebaseToken(
        token: String,
        accessToken: String,
    ){
        viewModelScope.launch {
            repository.setCurrentFirebaseToken(token,accessToken)
        }
    }

    private fun setFinishRegister(
        accessToken: String
    ){
        viewModelScope.launch {
            repository.setFinishRegister(accessToken)
            state = state.copy(finish_register = true)
        }
    }

    private fun login(
        id: String,
        code: String,
        phone: String,
    ){
        viewModelScope.launch {
            repository.login(
                id = id,
                code = code,
                phone = phone,
            )
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            if(result.data != null) {
                                getMe(accessToken = result.data.access_token)
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }

    private fun signUp(
        nickname: String,
        name: String,
        phone: String,
        default_profile_pic_id: String,
        id: String,
        code: String,
    ){
        viewModelScope.launch {
            repository.signUp(
                nickname = nickname,
                phone = phone,
                name = name,
                default_profile_pic_id = default_profile_pic_id,
                id = id,
                code = code
            )
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            if(result.data != null){
                                Log.e("singUpRepository", result.data.toString())
                                getMe(accessToken = result.data.access_token)
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }
    private fun logOut(){
        repository.clearProfile()
        state = state.copy(
            profile = null
        )
    }

    private fun getMe(
        accessToken: String = repository.data.access_token
    ){
        viewModelScope.launch {
            repository.getMe(accessToken)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            state = state.copy(
                                profile = result.data
                            )
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }

}