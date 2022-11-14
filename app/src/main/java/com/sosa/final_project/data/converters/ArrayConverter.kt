package com.sosa.final_project.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

// type converter passed to database so it knows how to store lists of strings
class ArrayConverter {

    @TypeConverter
    fun listToJson(value: MutableList<String>) : String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) : MutableList<String> {
        return Gson().fromJson(value, Array<String>::class.java).toMutableList()
    }
}