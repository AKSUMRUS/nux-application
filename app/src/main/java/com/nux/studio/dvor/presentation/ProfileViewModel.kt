package com.nux.studio.dvor.presentation

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nux.studio.dvor.data.local.profile.ProfileEvent
import com.nux.studio.dvor.data.local.profile.ProfileState
import com.nux.studio.dvor.data.repository.ProfileRepository
import com.nux.studio.dvor.domain.profile.UpdateProfileJSON
import com.nux.studio.dvor.presentation.error.ErrorMapper
import com.nux.studio.dvor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: ProfileRepository
) : ViewModel() {
    var state by mutableStateOf(ProfileState())
    private val errorMapper = ErrorMapper(context)

    private var job = Job()
        get() {
            if (field.isCancelled) field = Job()
            return field
        }

    fun onEvent(event: ProfileEvent) {
        when (event) {
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
                updateAvatar(profile_pic = event.profile_pic)
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
            repository.getDefaultProfilePics().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            defaultProfilePicsList = result.data!!.default_profile_pics
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
                    when (result) {
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
    ) {
        viewModelScope.launch {
            repository.confirmationPhone(phone = phone, reason = reason).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            id_confirmation_phone = result.data!!.id,
                            verifyErrorMessage = ""
                        )
                    }
                    is Resource.Loading -> Unit
                    is Resource.Error -> {
                        state = state.copy(
                            verifyErrorMessage = errorMapper.map(result.message) ?: ""
                        )
                    }
                }
            }
        }
    }

    private fun updateProfileData(newProfile: UpdateProfileJSON) {
        job.cancel()
        viewModelScope.launch(job) {
            repository
                .updateProfileData(newProfile = newProfile)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            val newProfileState = state.profile
                            newProfileState!!.name = result.data!!.name
                            newProfileState.nickname = result.data.nickname

                            Log.d("updateProfile", "Finish $newProfileState")
                            state = state.copy(
                                profile = newProfileState
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

    private fun getProfile() {
        viewModelScope.launch {
            val response = repository.getProfile()
            state = state.copy(
                profile = response,
                finish_register = response?.finishRegister ?: state.finish_register
            )
        }
    }

    private fun updateAvatar(
        profile_pic: Bitmap
    ) {
        viewModelScope.launch {
            repository.uploadAvatar(
                profile_pic_bitmap = profile_pic,
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data != null) {
                            getMe()
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

    private fun inviteFriends(accessToken: String, friends_ids: List<String>, app_id: String) {
        viewModelScope.launch {
            repository.inviteFriends(
                accessToken = accessToken,
                friends_ids = friends_ids,
                app_id = app_id
            )
        }
    }

    fun setCurrentFirebaseToken(
        token: String,
        accessToken: String,
    ) {
        viewModelScope.launch {
            repository.setCurrentFirebaseToken(token, accessToken)
        }
    }

    private fun setFinishRegister(
        accessToken: String
    ) {
        viewModelScope.launch {
            repository.setFinishRegister(accessToken)
            state = state.copy(finish_register = true)
        }
    }

    private fun login(
        id: String,
        code: String,
        phone: String,
    ) {
        viewModelScope.launch {
            repository.login(
                id = id,
                code = code,
                phone = phone,
            )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (result.data != null) {
                                getMe()
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
    ) {
        viewModelScope.launch {
            repository.signUp(
                nickname = nickname,
                phone = phone,
                name = name,
                default_profile_pic_id = default_profile_pic_id,
                id = id,
                code = code
            )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (result.data != null) {
                                Log.i("singUpRepository", result.data.toString())
                                getMe()
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

    private fun logOut() {
        repository.clearProfile()
        state = state.copy(
            profile = null
        )
    }

    private fun getMe() {
        viewModelScope.launch {
            repository.getMe()
                .collect { result ->
                    when (result) {
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