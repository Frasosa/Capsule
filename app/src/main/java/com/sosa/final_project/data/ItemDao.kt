package com.sosa.final_project.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
// interface for items in database
interface ItemDao {
    // method to retrieve all items from the database
    @Query("SELECT * from item_database")
    fun getWardrobe(): LiveData<List<Item>>

    // method to insert a item into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: Item)

    // method to delete a item from the database
    @Delete
    suspend fun deleteItem(item: Item)
}