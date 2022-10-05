package com.ledokol.thebestproject.ui.components.molecules.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonEmpty
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun GameActivity(
    packageName: String,
    gameName: String,
    iconPreview: String?,
    in_app: Boolean,
    startTime: String = "",
    finishTime: String = "",
    onClick: () -> Unit = {},
){

//    startTime = startTime.subSequence(0,19).toString()

    val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")

    val localDateTimeStart = LocalDateTime.parse(startTime).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.systemDefault())
    var localDateTimeFinish: ZonedDateTime = ZonedDateTime.now()
    if(!in_app){
        localDateTimeFinish = LocalDateTime.parse(finishTime).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.systemDefault())
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

    fun getDay(dateTime: ZonedDateTime): String{
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

    fun getTime(dateTime: ZonedDateTime): String{
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return dateTime.format(formatter)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.primary)
            .clickable {
                onClick()
            }
            .padding(top = 0.dp),
    ) {
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
                    .align(CenterVertically),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ){
                Body1(
                    text = gameName,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(bottom =5.dp)
                )

                if(!in_app){
                    Body1(text = "Был ${getDay(localDateTimeFinish)} c ${getTime(localDateTimeStart)} до ${getTime(localDateTimeFinish)} ")
//                    Body1(text = "")
                }else{
                    Body1(text = "Сейчас использует")
                }
            }
        }
    }
}
