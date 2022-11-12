package com.sosa.final_project.data

import android.graphics.Bitmap
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class ArrayConverter {

    @TypeConverter
    fun listToJson(value: MutableList<String>) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) : MutableList<String> {
        return Gson().fromJson(value, Array<String>::class.java).toMutableList()
    }

//    @TypeConverter
//    fun stringToSomeObjectList(data: String?): List<String?>? {
//        if (data == null) {
//            return Collections.emptyList()
//        }
//        val listType: Type = object : TypeToken<List<String?>?>() {}.type
//        return Gson().fromJson(data, listType)
//    }
//
//    @TypeConverter
//    fun someObjectListToString(someObjects: List<String?>?): String? {
//        return Gson().toJson(someObjects)
//    }
}