package com.ledokol.thebestproject.data.local

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.ledokol.thebestproject.data.local.user.Status
import com.ledokol.thebestproject.data.local.user.User
import java.io.ByteArrayOutputStream
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    @TypeConverter
    fun storedStringToMyObjects(data: String?): Status? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        val listType: Type = object : TypeToken<Status?>() {}.type
        return gson.fromJson<Status?>(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: Status?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }

    @TypeConverter
    fun storedStringToMyObject(data: String?): User? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        val listType: Type = object : TypeToken<User?>() {}.type
        return gson.fromJson<User?>(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredStrings(myObjects: User?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }

//    @TypeConverter
//    fun fromGameToStatusJSON(games: List<Game>): List<StatusJSON>? {
//        val res: MutableList<StatusJSON> = mutableListOf()
//        for (game in games){
//            res.add(StatusJSON(game.android_package_name,game.name,game.category))
//        }
//        return res.toList()
//    }

    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        val listType = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}