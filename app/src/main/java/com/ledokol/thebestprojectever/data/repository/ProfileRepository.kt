package com.ledokol.thebestprojectever.data.repository

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ledokol.thebestprojectever.data.local.profile.DoNotDisturb
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileDao
import com.ledokol.thebestprojectever.data.local.profile.ProfileToken
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.data.remote.RetrofitServicesCloud
import com.ledokol.thebestprojectever.domain.FirebaseToken
import com.ledokol.thebestprojectever.domain.FriendsInviteToGame
import com.ledokol.thebestprojectever.domain.ProfileJSON
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val api : RetrofitServices,
    private val apiCloud: RetrofitServicesCloud,
    private val dao: ProfileDao
    ) {

    var data by mutableStateOf(Profile(access_token = ""))

    fun setCurrentFirebaseToken(token: String, accessToken: String){
        Log.e("setCurrentFirebaseToken","$token $accessToken")
        val callSetCurrentToken = api.setCurrentFirebaseToken(
            authHeader = "Bearer $accessToken",
            FirebaseToken(firebase_messaging_token = token)
        )

        callSetCurrentToken.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("setCurrentFirebaseToken",response.body().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("setCurrentFirebaseToken","Errrr")
            }

        })
    }

    fun convertBitmapToPNG(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun uploadAvatar(
        accessToken: String,
        profile_pic_bitmap: Bitmap,
    ){
        val TAG = "uploadAvatar"

        val out = convertBitmapToPNG(profile_pic_bitmap)

        val requestBody: RequestBody = RequestBody.create("image/png".toMediaTypeOrNull(),out)
        val profile_pic: MultipartBody.Part = MultipartBody.Part.createFormData("profile_pic", "profile_pic.png", requestBody)

        Log.e(TAG, "$accessToken")
        val callUpdateAvatar = api.uploadAvatar(
            authHeader = "Bearer $accessToken",
            profile_pic = profile_pic
        )

        callUpdateAvatar.enqueue(object : Callback<Profile>{
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                Log.e(TAG, "onResponse ${response.body()!!.toString()}")
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Log.e(TAG, "Error ${t.message}")
            }

        })
    }

    fun clearProfile(){
        dao.clearProfile()
    }

    fun inviteFriends(
        accessToken: String,
        friends_ids: List<String>,
        app_id: String
    ){
        Log.e("INVITE!!", FriendsInviteToGame(friends_ids = friends_ids, app_id = app_id).toString())
        val callInvite = api.friendsInvite(
            authHeader = "Bearer $accessToken",
            friends = FriendsInviteToGame(
                friends_ids = friends_ids,
                app_id = app_id
            )
        )
        callInvite.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.e("INVITE!",response.toString())
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun setDoNotDisturb(
        canDisturb: Boolean,
        accessToken: String
    ) : Flow<Resource<Profile>> {
        return flow{
            emit(Resource.Loading(true))

            val remoteProfile = try{
                val updateCall = api.setDoNotDisturb(
                    authHeader = "Bearer $accessToken",
                    DoNotDisturb(do_not_disturb = canDisturb)
                )

                val profile = updateCall.awaitResponse().body()

                profile
            }
            catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteProfile?.let { profile ->
                dao.clearProfile()
                profile.access_token = accessToken
                dao.insertProfile(profile = profile)

                emit(Resource.Success(
                    data = profile
                ))

                emit(Resource.Loading(false))
            }

        }
    }

    fun updateProfileData(newProfile: Profile){
        // Обращение к серваку
        Log.e("Update Profile Data","updated")
    }

    fun getProfile() : Profile?
    {

        if(dao.getProfile() != null) {
            data = dao.getProfile()!!
        }
        return dao.getProfile()
    }

    fun setFinishRegister(
        accessToken: String
    ){
        dao.finishRegister(accessToken,true)
    }

    fun signUp(
        nickname: String,
        name: String,
        password: String
    ): Flow<Resource<ProfileToken>>{
        return flow {
            emit(Resource.Loading(true))

            val remoteUser = try{
                val callRegister = api.createProfile(ProfileJSON(nickname = nickname,password = password,name = name))
                val myResponse = callRegister.awaitResponse().body()

                myResponse
            }
            catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteUser?.let { profile ->

                emit(Resource.Success(
                    data = profile
                ))

                emit(Resource.Loading(false))

            }

        }
    }

    fun login(
        nickname: String,
        password: String
    ): Flow<Resource<ProfileToken>>{
        return flow {
            emit(Resource.Loading(true))

            val remoteUser = try {
                val callLogin = api.login(nickname = nickname,password = password)
                val myResponse = callLogin.awaitResponse().body()

                myResponse
            }
            catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteUser?.let { profile ->

                Log.e("AccessToken",profile.toString())

                emit(Resource.Success(
                    data = profile
                ))

                emit(Resource.Loading(false))
            }

        }
    }

    fun getMe(
        accessToken: String
    ): Flow<Resource<Profile>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteProfile = try{
                val profileCall = api.getMe(authHeader = "Bearer $accessToken")
                val myResponse: Profile? = profileCall.awaitResponse().body()
                myResponse?.access_token = accessToken

                myResponse
            }
            catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteProfile?.let { profile ->
                dao.clearProfile()
                dao.insertProfile(profile)
                Log.e("Insert Profile",profile.toString())

                data = dao.getProfile()!!

                emit(Resource.Success(
                    data = dao.getProfile()
                ))

                emit(Resource.Loading(false))
            }

        }
    }

}
