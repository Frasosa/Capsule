package com.sosa.final_project.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    // method to retrieve all items from the database
    @Query("SELECT * from item_database")
    fun getAllItems(): LiveData<List<Item>>

    // method to retrieve a item from the database by id
    @Query("SELECT * from item_database WHERE id = :id")
    fun getItem(id: Int): LiveData<Item>

    // method to insert a item into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: Item)

    // method to update a item that is already in the database
    @Update
    suspend fun updateItem(item: Item)

    // method to delete a item from the database.
    @Delete
    suspend fun deleteItem(item: Item)
}