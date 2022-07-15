package com.ledokol.thebestprojectever.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileDao
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.data.remote.RetrofitServicesCloud
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val api : RetrofitServices,
    private val apiCloud: RetrofitServicesCloud,
    private val dao: ProfileDao
    ) {
    val profile: LiveData<Profile> = dao.getProfile()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun setCurrentFirebaseToken(token: String){
        coroutineScope.launch {
            api.setCurrentFirebaseToken(token)
        }
    }

    fun insertProfile(newProfile: Profile){
        coroutineScope.launch {
            Log.e("Insert Profile",newProfile.toString())
            dao.insertProfile(newProfile)
        }
    }

    fun clearProfile(){
        coroutineScope.launch {
            dao.clearProfile()
        }
    }

    fun setFinishRegister(){
        coroutineScope.launch {
            dao.finishRegister(profile.value!!.access_token,true)
        }
    }

//    fun getProfile(){
//        coroutineScope.launch {
//            profile.value = profileDao.getProfile()
//        }
//    }

    fun sendInvite(){
        coroutineScope.launch {
//            profileDao.sendInvite()
        }
    }

}