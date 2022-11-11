package com.sosa.final_project.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {
    // method to retrieve an outfit from the database by id
    @Query("SELECT * from outfit_database WHERE day = :day")
    fun getOutfit(day: String): Flow<Outfit>

    // method to insert a outfit into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOutfit(outfit: Outfit)

    // method to update a outfit that is already in the database
    @Update
    suspend fun updateOutfit(outfit: Outfit)

    // method to delete a outfit from the database.
    @Delete
    suspend fun deleteOutfit(outfit: Outfit)
}