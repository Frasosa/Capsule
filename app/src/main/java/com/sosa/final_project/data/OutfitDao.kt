package com.sosa.final_project.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
// interface for outfits in the database
interface OutfitDao {
    // method to retrieve an outfit from the database by id
    @Query("SELECT * from outfit_database WHERE day = :day")
    fun getOutfit(day: String): LiveData<Outfit>

    // method to insert a outfit into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOutfit(outfit: Outfit)

    // method to update a outfit that is already in the database
    @Update
    suspend fun updateOutfit(outfit: Outfit)
}