package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun GameActivity(
    packageName: String,
    iconPreview: String?,
    imageWide: String?,
    in_app: Boolean,
    startTime: String = "",
    finishTime: String = "",
    onClick: () -> Unit = {},
){

    val localDateTimeStart = LocalDateTime.parse(startTime)
    var localDateTimeFinish: LocalDateTime = LocalDateTime.now()
    if(!in_app){
        localDateTimeFinish = LocalDateTime.parse(finishTime)
    }
    val localDateTimeCurrent = LocalDateTime.now()

    fun fromEnglishToRussianDay(day: String):String{
        val russianDay = when(day){
            "JANUARY" -> "Января"
            "FEBRUARY" -> "Февраля"
            "MARCH" -> "Марта"
            "APRIL" -> "Апреля"
            "MAY" -> "Мая"
            "JUNE" -> "Июня"
            "JULY" -> "Июля"
            "AUGUST" -> "Августа"
            "SEPTEMBER" -> "Сентября"
            "OCTOBER" -> "Октября"
            "NOVEMBER" -> "Ноября"
            "DECEMBER" -> "Декабря"
            else -> {"Неизвестно"}
        }

        return russianDay
    }

    fun getDay(dateTime: LocalDateTime): String{
        if(dateTime.month == localDateTimeCurrent.month && dateTime.dayOfMonth == localDateTimeCurrent.dayOfMonth){
            return "сегодня"
        }
        if(dateTime.month == localDateTimeCurrent.month && dateTime.dayOfMonth == localDateTimeCurrent.dayOfMonth-1){
            return "вчера"
        }
        if(dateTime.month == localDateTimeCurrent.month && dateTime.dayOfMonth >= localDateTimeCurrent.dayOfMonth-2){
            return "позавчера"
        }

        return "${localDateTimeFinish.dayOfMonth} ${fromEnglishToRussianDay(
            localDateTimeFinish.month.toString()
        )}"
    }

    fun getTime(dateTime: LocalDateTime): String{
        val formatter = DateTimeFormatter.ofPattern("hh:mm")
        return "${formatter.format(dateTime)}"
    }

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable {
                onClick()
            }
            .padding(top = 20.dp)
        ,
    ) {
        AsyncImage(
            model = imageWide,
            contentDescription = "GameImage",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .alpha(0.3f)
            ,
            contentScale = ContentScale.Crop,
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
            ,
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = iconPreview,
                contentDescription = "GameImage",
                modifier = Modifier
                    .size(height = 100.dp, width = 100.dp)
                    .padding(15.dp)
                    .align(CenterVertically)
                ,
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ){
                if(!in_app){
                    Body1(text = "Играл ${getDay(localDateTimeFinish)}")
                    Body1(text = "c ${getTime(localDateTimeStart)} до ${getTime(localDateTimeFinish)} ")
                }else{
                    Body1(text = "Сейчас играет")
                }
            }
        }
    }

}