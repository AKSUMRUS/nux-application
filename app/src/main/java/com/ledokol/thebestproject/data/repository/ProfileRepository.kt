package com.ledokol.thebestproject.data.repository

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ledokol.thebestproject.data.local.profile.DoNotDisturb
import com.ledokol.thebestproject.data.local.profile.Profile
import com.ledokol.thebestproject.data.local.profile.ProfileDao
import com.ledokol.thebestproject.data.local.profile.ProfileToken
import com.ledokol.thebestproject.data.local.token.TokenEntity
import com.ledokol.thebestproject.data.remote.RetrofitServices
import com.ledokol.thebestproject.domain.games.FriendsInviteToGame
import com.ledokol.thebestproject.domain.profile.*
import com.ledokol.thebestproject.util.Resource
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
    private val tokenRepository: TokenRepository,
    private val api : RetrofitServices,
    private val dao: ProfileDao
    ) {

    var data by mutableStateOf(Profile(access_token = ""))

    fun setCurrentFirebaseToken(
        token: String,
        accessToken2: String
    ){
        val accessToken = tokenRepository.getToken()
        Log.e("myFirebaseToken","$token $accessToken")
        val callSetCurrentToken = api.setCurrentFirebaseToken(
            authHeader = "Bearer $accessToken",
            FirebaseToken(firebase_messaging_token = token)
        )

        callSetCurrentToken.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("myFirebaseToken","Success ${response.body().toString()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("myFirebaseToken","Errrr")
            }

        })
    }

    fun convertBitmapToPNG(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun convertBitmapToJPEG(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
//        val compressedImageFile = Compressor.compress(this, byteArray) {
//            resolution(1280, 720)
//            quality(80)
//            format(Bitmap.CompressFormat.WEBP)
//            size(2_097_152) // 2 MB
//        }
        return byteArrayOutputStream.toByteArray()
    }

    fun uploadAvatar(
        accessToken: String,
        profile_pic_bitmap: Bitmap,
    ): Flow<Resource<Profile>>{

        return flow{
            emit(Resource.Loading(true))
            val TAG = "uploadAvatar"

            val token = tokenRepository.getToken()

            val out = convertBitmapToPNG(profile_pic_bitmap)

            val requestBody: RequestBody = RequestBody.create("image/png".toMediaTypeOrNull(),out)
            val profile_pic: MultipartBody.Part = MultipartBody.Part.createFormData("profile_pic", "profile_pic.png", requestBody)

            Log.e(TAG, "$token")
            val remoteProfile = try{
                val callUpdateAvatar = api.uploadAvatar(
                    authHeader = "Bearer $token",
                    profile_pic = profile_pic
                )
                val profile = callUpdateAvatar.awaitResponse().body()

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
                emit(Resource.Success(
                    data = profile
                ))

                emit(Resource.Loading(false))
            }
        }
    }

    fun confirmationPhone(
        phone: String,
        reason: String,
    ): Flow<Resource<ResponseConfirmationPhone>> {
        return flow{
            Log.e("confirmationPhone","$phone $reason")
            val callConfirmationPhone = api.confirmationPhone(
                confirmationPhone = ConfirmationPhone(phone = phone, reason = reason)
            )

            val response = try{
                val responseApi = callConfirmationPhone.awaitResponse()
                if(responseApi.code() == 200){
                    responseApi.body()
                }
                else{
                    emit(Resource.Error("Что-то пошло не так... Попробуй еще раз"))
                    null
                }
            }catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't connect to server"))
                null
            }

            response?.let{
                emit(Resource.Success(
                    data = response
                ))
            }
        }

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

        val token = tokenRepository.getToken()

        val callInvite = api.friendsInvite(
            authHeader = "Bearer $token",
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

            val token = tokenRepository.getToken()

            val remoteProfile = try{
                val updateCall = api.setDoNotDisturb(
                    authHeader = "Bearer $token",
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
                profile.access_token = token
                dao.insertProfile(profile = profile)

                tokenRepository.addToken(profile.access_token)

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

    fun getProfile(
        fetchRemote: Boolean = false,
    ) : Profile?
    {

        if(dao.getProfile() != null) {
            data = dao.getProfile()!!
        }
        return dao.getProfile()
    }

    fun setFinishRegister(
        accessToken: String
    ){
        val token = tokenRepository.getToken()

        dao.finishRegister(token,true)
    }

    fun signUp(
        nickname: String,
        name: String,
        phone: String,
        default_profile_pic_id: String,
        id: String,
        code: String,
    ): Flow<Resource<ProfileToken>>{
        return flow {
            emit(Resource.Loading(true))

            Log.e("singUpRepository","$nickname $name $phone $id $code")
            val remoteUser = try{
                val callRegister = api.createProfile(RegisterJSON(
                    user = ProfileJSON(
                        nickname = nickname,
                        name = name,
                        phone = phone,
                        default_profile_pic_id = default_profile_pic_id,
                        password = "1",
                    ),
                    phone_confirmation = ConfirmationPhoneAuth(id = id, code = code)
                ))
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

            Log.e("singUpRepository", remoteUser.toString())

            remoteUser?.let { profile ->

                emit(Resource.Success(
                    data = profile
                ))

                emit(Resource.Loading(false))

            }

        }
    }

    fun login(
        id: String,
        code: String,
        phone: String,
    ): Flow<Resource<ProfileToken>>{
        return flow {
            emit(Resource.Loading(true))

            val TAG = "loginScreen"
            Log.e(TAG,"start $id $code $phone")

            val remoteUser = try {
                val callLogin = api.login(
                    LoginJSON(
                        phone = phone,
                        phone_confirmation = ConfirmationPhoneAuth(id = id, code = code)
                    )
                )
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

            Log.e(TAG,"res ${remoteUser.toString()}")

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

            val token = tokenRepository.getToken()
            Log.e("uploadAvatar","start")
            emit(Resource.Loading(true))

            val remoteProfile = try{
                val profileCall = api.getMe(authHeader = "Bearer $token")
                val myResponse: Profile? = profileCall.awaitResponse().body()
                myResponse?.access_token = token

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

            Log.e("getMeProfile","good")
            remoteProfile?.let { profile ->
                dao.clearProfile()
                dao.insertProfile(profile)
                Log.e("Insert_Profile",profile.toString())

                tokenRepository.addToken(token = profile.access_token)

                data = dao.getProfile()!!

                emit(Resource.Success(
                    data = dao.getProfile()
                ))

                emit(Resource.Loading(false))
            }
        }
    }

    fun getDefaultProfilePics() : Flow<Resource<DefaultProfilePicsList>>{
        return flow{
            emit(Resource.Loading(true))

            val callProfilePics = api.getDefaultProfilePics()

            val profilePics = callProfilePics.awaitResponse().body()

            Log.e("profilePics", profilePics.toString())

            emit(Resource.Success(
                data = profilePics
            ))

            emit(Resource.Loading(false))
        }
    }

}
