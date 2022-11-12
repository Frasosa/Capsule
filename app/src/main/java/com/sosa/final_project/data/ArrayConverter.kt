package com.sosa.final_project.data

import android.graphics.Bitmap
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class ArrayConverter {

//    @TypeConverter
//    fun listToJson(value: MutableList<Bitmap>) : String {
//        return Gson().toJson(value)
//    }
//
//    @TypeConverter
//    fun jsonToList(value: String) : MutableList<Bitmap> {
//        return Gson().fromJson(value, Array<Bitmap>::class.java).toMutableList()
//    }

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Bitmap?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Bitmap?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Bitmap?>?): String? {
        return Gson().toJson(someObjects)
    }
}