package com.sosa.final_project.data

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

//Represents an item in your wardrobe
@Entity(tableName = "item_database")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @DrawableRes val imageResourceId: Int,
    val category: Category
)
