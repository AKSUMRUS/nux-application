package com.ledokol.thebestprojectever.presentation

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileDatabase
import com.ledokol.thebestprojectever.data.repository.ProfileRepository

class MainViewModel(application: Application): ViewModel() {
    val profile: LiveData<List<Profile>>
    private val repository: ProfileRepository

    init {
        val profileDb = ProfileDatabase.getInstance(application)
        val profileDao = profileDb.profileDao()
        repository = ProfileRepository(profileDao)

        profile = repository.profile
    }

    fun insertProfile(profile: Profile) {
        repository.insertProfile(profile)
    }

    fun clearProfile(){
        repository.clearProfile()
    }

}