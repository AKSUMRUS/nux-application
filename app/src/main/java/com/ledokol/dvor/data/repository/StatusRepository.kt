package com.ledokol.dvor.data.repository

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.ledokol.dvor.data.local.status.StatusState
import com.ledokol.dvor.data.remote.RetrofitServices
import com.ledokol.dvor.domain.games.App
import com.ledokol.dvor.domain.games.StatusJSON
import com.ledokol.dvor.domain.statistics.GameSessionStatistics
import com.ledokol.dvor.domain.statistics.GameSessionStatisticsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StatusRepository @Inject constructor(
    private val api: RetrofitServices
) {

    val state by mutableStateOf(StatusState())

    fun setStatus(
        androidPackageName: String,
        name: String,
        androidCategory: Int,
    ) {
        state.androidPackageName = androidPackageName
        val now = Calendar.getInstance().time
        state.beginTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(now)


        Log.i(
            "PUT STATUS!",
            "androidPackageName = ${state.androidPackageName}, beginTime = ${state.beginTime}"
        )


        api.setStatus(
            App(
                app = StatusJSON(
                    android_package_name = androidPackageName,
                    name = name,
                    android_category = androidCategory
                )
            )
        ).enqueue(object : Callback<StatusJSON> {
            override fun onResponse(call: Call<StatusJSON>, response: Response<StatusJSON>) {
                Log.i("SetStatus", "Status has set $androidPackageName ")
            }

            override fun onFailure(call: Call<StatusJSON>, t: Throwable) {
                Log.i("SetStatus", t.toString())
            }

        })
    }

    fun leaveStatus(

    ) {
        putStatistics()

        api.leaveStatus().enqueue(object : Callback<StatusJSON> {
            override fun onResponse(call: Call<StatusJSON>, response: Response<StatusJSON>) {
                Log.e("Leave Status", "Status has left")
            }

            override fun onFailure(call: Call<StatusJSON>, t: Throwable) {

                Log.e("Leave Status", t.toString())
            }

        })
    }

    fun putStatistics(
    ) {
        if (state.androidPackageName == null || state.beginTime == null) return

        val now = Calendar.getInstance().time
        val endTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(now)

        Log.i(
            "PUT STATUS",
            "androidPackageName = ${state.androidPackageName}, beginTime = ${state.beginTime}, endTime = $endTime"
        )

        api.putStatistics(
            GameSessionStatisticsList(
                listOf(
                    GameSessionStatistics(
                        android_package_name = state.androidPackageName!!,
                        dt_begin = state.beginTime!!,
                        dt_end = endTime,
                    )
                )
            )
        ).enqueue(object : Callback<Any> {
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                Log.i("PutStatistics", response.toString())
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i("PutStatistics", t.toString())
            }

        })
    }
}