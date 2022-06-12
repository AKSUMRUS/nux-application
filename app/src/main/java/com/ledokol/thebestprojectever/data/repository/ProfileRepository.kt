package com.ledokol.thebestprojectever.data.repository

import android.text.BoringLayout
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ledokol.thebestprojectever.data.local.Profile
import com.ledokol.thebestprojectever.data.local.ProfileDao
import kotlinx.coroutines.*

class ProfileRepository(
    private val profileDao: ProfileDao
    ) {
    val profile: LiveData<List<Profile>> = profileDao.getProfile()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertProfile(newProfile: Profile){
        coroutineScope.launch {
            profileDao.insertProfile(newProfile)
        }
    }

    fun clearProfile(){
        coroutineScope.launch {
            profileDao.clearProfile()
        }
    }

//    fun getProfile(){
//        coroutineScope.launch {
//            profile.value = profileDao.getProfile()
//        }
//    }

}