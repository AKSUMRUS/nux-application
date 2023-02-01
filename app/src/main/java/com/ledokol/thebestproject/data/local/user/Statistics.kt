package com.ledokol.thebestproject.data.local.user

import com.squareup.moshi.Json

data class Statistics(
    val installed: Boolean = true,
    @field:Json(name = "activity_last_two_weeks")
    val activityLastTwoWeeks: Int = 0,
    @field:Json(name = "activity_total")
    val activityTotal: Int = 0,
    @field:Json(name = "dt_last_activity")
    val lastActivityDate: String = ""
)
