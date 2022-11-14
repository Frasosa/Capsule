package com.sosa.final_project.data

import android.content.Context
import androidx.room.*
import com.sosa.final_project.data.converters.ArrayConverter
import com.sosa.final_project.data.converters.BitmapConverter
import com.sosa.final_project.data.converters.OutfitConverter


/**
 * Room database to persist data for the Capsule app.
 * This database stores a [Item] entity
 */
@Database(entities = [Item::class, Outfit::class], version = 2, exportSchema = false)
@TypeConverters(BitmapConverter::class, OutfitConverter::class, ArrayConverter::class)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun outfitDao(): OutfitDao

    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getDatabase(context: Context): ItemDatabase {
            val tempInstance = INSTANCE
            //If one exists already we can just return it
            if (tempInstance != null)
                return tempInstance

            //Otherwise we make one and return it with synchronization
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }


}