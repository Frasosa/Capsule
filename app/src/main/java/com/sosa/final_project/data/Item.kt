package com.sosa.final_project.data

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

//Represents an item in your wardrobe
@Entity(tableName = "item_database")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: Bitmap
){

}

