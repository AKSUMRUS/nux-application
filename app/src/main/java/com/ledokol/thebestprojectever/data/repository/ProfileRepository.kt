package com.ledokol.thebestprojectever.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileDao
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
    ) {
    val profile: LiveData<List<Profile>> = profileDao.getProfile()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertProfile(newProfile: Profile){
        coroutineScope.launch {
            Log.e("Insert Profile",newProfile.toString())
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