package com.sosa.final_project.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

// represents an outfit for a day
@Entity(tableName = "outfit_database")
data class Outfit(
    @PrimaryKey
    val day : String,
    var items : MutableList<String>
)
