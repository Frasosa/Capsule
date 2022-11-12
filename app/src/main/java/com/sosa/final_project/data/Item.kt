package com.sosa.final_project.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

// represents an item in the wardrobe
@Entity(tableName = "item_database")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: Bitmap,
    //val selected: Boolean
)

