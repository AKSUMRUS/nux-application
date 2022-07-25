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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.RequestBody
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
                login(nickname = event.nickname, password = event.password)
            }
            is ProfileEvent.UpdateAvatar -> {
                updateAvatar(accessToken = event.accessToken, profile_pic = event.profile_pic)
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
                    password = event.password,
                    email = event.email,
                    name = event.name
                )
            }
            is ProfileEvent.SetCurrentFirebaseToken -> {
                setCurrentFirebaseToken(token = event.token, accessToken = event.accessToken)
            }
            is ProfileEvent.SetFinishRegister -> {
                setFinishRegister(event.accessToken)
            }
            is ProfileEvent.SetDoNotDisturb -> {
                setDoNotDisturb(event.canDisturb)
            }
        }
    }

    private fun setDoNotDisturb(
      canDisturb: Boolean
    ) {
        viewModelScope.launch {
            repository.setDoNotDisturb(canDisturb = canDisturb)
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

    private fun updateProfileData(newProfile: Profile){
        viewModelScope.launch {
            repository.updateProfileData(newProfile = newProfile)
        }
    }

    private fun getProfile(){
        val response = repository.getProfile()
        if(response != null) {
            state = state.copy(
                profile = response,
                finish_register = response.finishRegister
            )
        }
        else{
            state = state.copy(
                profile = response
            )
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
            )
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

    private fun login(nickname: String, password: String){
        viewModelScope.launch {
            repository.login(nickname = nickname,password = password)
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
        password: String,
        email: String,
        name: String,
    ){
        viewModelScope.launch {
            repository.signUp(nickname = nickname,password = password, name = name)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            if(result.data != null){
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
        accessToken: String
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