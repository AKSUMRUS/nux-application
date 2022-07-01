package com.ledokol.thebestprojectever.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.data.repository.ProfileRepository
import com.ledokol.thebestprojectever.domain.ProfileJSON
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val api: RetrofitServices
): ViewModel() {
    val profile: LiveData<List<Profile>> = repository.profile

    fun insertProfile(profile: Profile) {
        repository.insertProfile(profile)
    }

    fun login(nickname: String,password: String){
        val profileCall : Call<Profile> = api.login(
            nickname = nickname,
            password = password
        )
        profileCall.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
                    insertProfile(Profile(access_token = response.body()!!.access_token,nickname = nickname,password = password))
                }
                else{
                    Log.e("pshel nahui",response.code().toString())
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Log.e("ERRR",t.toString())
            }
        })
    }

    fun signUp(nickname: String,password: String){
        val query = ProfileJSON(nickname = nickname,password = password)
        Log.e("Tock",query.toString())
        val profileCall : Call<Profile> = api.createProfile(
            query
        )
        profileCall.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
                    Log.e("ERRtR",response.body().toString())
                    insertProfile(Profile(access_token = response.body()?.access_token.toString(),nickname = nickname,password = password))
                }
                else{
                    Log.e("Err",response.code().toString())
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Log.e("ERRR",t.toString())
            }
        })
    }

    fun clearProfile(){
        repository.clearProfile()
    }

}