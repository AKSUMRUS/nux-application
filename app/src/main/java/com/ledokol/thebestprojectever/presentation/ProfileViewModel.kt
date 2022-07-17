package com.ledokol.thebestprojectever.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileState
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.data.repository.ProfileRepository
import com.ledokol.thebestprojectever.domain.FriendsInviteToGame
import com.ledokol.thebestprojectever.ui.navigation.BottomNavItemMain
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val api: RetrofitServices,

): ViewModel() {
    var state by mutableStateOf(ProfileState())

    fun getProfile(){
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

    fun inviteFriends(accessToken: String, friends_ids: List<String>, app_id: String){
        api.friendsInvite(
            authHeader = "Bearer $accessToken",
            friends = FriendsInviteToGame(
                friends_ids = friends_ids,
                app_id = app_id
            )
        )
    }

    fun setCurrentFirebaseToken(token: String){
        viewModelScope.launch {
            repository.setCurrentFirebaseToken(token)
        }
    }

    fun setFinishRegister(
        accessToken: String
    ){
        viewModelScope.launch {
            repository.setFinishRegister(accessToken)
            state = state.copy(finish_register = true)
        }
    }

    fun login(nickname: String, password: String){
        viewModelScope.launch {
            repository.login(nickname = nickname,password = password)
                .collect(){ result ->
                    when(result){
                        is Resource.Success -> {
                            if(result.data != null) {
                                getMe(accessToken = result.data.access_token)
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> Unit
                    }
                }
        }
    }

    fun signUp(
        nickname: String,
        password: String,
        email: String,
        name: String,
    ){
        viewModelScope.launch {
            repository.signUp(nickname = nickname,password = password, name = name)
                .collect(){ resilt ->
                    when(resilt){
                        is Resource.Success -> {
                            if(resilt.data != null){
                                getMe(accessToken = resilt.data.access_token)
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> Unit
                    }
                }
        }
    }
    fun logOut(){
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
                .collect(){ result ->
                    when(result){
                        is Resource.Success -> {
                            state = state.copy(
                                profile = result.data
                            )
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> Unit
                    }
                }
        }
    }

}