package com.ledokol.thebestprojectever.data.repository

import android.util.Log
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.domain.StatusJSON
import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StatusRepository @Inject constructor(
    private val api : RetrofitServices
) {

    fun setStatus(
        androidPackageName : String,
        name : String,
        androidCategory : String
    ){
        api.setStatus(StatusJSON(androidPackageName = androidPackageName,name = name,androidCategory = androidCategory)).enqueue(object : Callback<StatusJSON> {
            override fun onResponse(call: Call<StatusJSON>, response: Response<StatusJSON>) {
                Log.e("SetStatus","Status has set")
            }

            override fun onFailure(call: Call<StatusJSON>, t: Throwable) {
                Log.e("SetStatus",t.toString())
            }

        })
    }

    fun leaveStatus(){
        api.leaveStatus().enqueue(object : Callback<StatusJSON> {
            override fun onResponse(call: Call<StatusJSON>, response: Response<StatusJSON>) {
                Log.e("Leave Status","Status has left")
            }

            override fun onFailure(call: Call<StatusJSON>, t: Throwable) {
                Log.e("Leave Status",t.toString())
            }

        })
    }

}