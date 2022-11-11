package com.sosa.final_project.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class ArrayConverter {

    @TypeConverter
    fun listToJson(value: MutableList<Item>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Item>::class.java).toMutableList()
}