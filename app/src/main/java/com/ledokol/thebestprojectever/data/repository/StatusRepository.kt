package com.ledokol.thebestprojectever.data.repository

import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.domain.StatusJSON
import com.squareup.moshi.Json
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
        api.setStatus(StatusJSON(androidPackageName = androidPackageName,name = name,androidCategory = androidCategory))
    }

    fun leaveStatus(){
        api.leaveStatus()
    }

}