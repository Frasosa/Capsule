package com.sosa.final_project.model

import android.graphics.Bitmap
import androidx.lifecycle.*
import androidx.room.util.copy
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.Outfit
import com.sosa.final_project.data.OutfitDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OutfitViewModelWIP(private val outfitDao: OutfitDao) : ViewModel() {

    var mondayLiveData: LiveData<Outfit> = outfitDao.getOutfit("monday")
    val tuesday: LiveData<Outfit> = outfitDao.getOutfit("tuesday")
    val wednesday: LiveData<Outfit> = outfitDao.getOutfit("wednesday")
    val thursday: LiveData<Outfit> = outfitDao.getOutfit("thursday")
    val friday: LiveData<Outfit> = outfitDao.getOutfit("friday")
    val saturday: LiveData<Outfit> = outfitDao.getOutfit("saturday")
    val sunday: LiveData<Outfit> = outfitDao.getOutfit("sunday")

    //var currentDay: Outfit


    //gets an item given an id from the database
    fun updateOutfit(item: Item) {
        if (mondayLiveData.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                println("Here")
                mondayLiveData.value?.items?.add(item.image.copy(item.image.config, true))
                outfitDao.updateOutfit(mondayLiveData.value!!)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val tmpList = mutableListOf<Bitmap>()
                tmpList.add(item.image.copy(item.image.config, true))
                outfitDao.insertOutfit(Outfit("monday", tmpList))
            }
        }
    }

    //deletes an item from the database
    fun deleteOutfit(outfit: Outfit) {
        viewModelScope.launch(Dispatchers.IO) {
            outfitDao.deleteOutfit(outfit)
        }
    }

    fun setOutfit(day: String) {
        //if (day == "monday")
            //mondayLiveData = outfitDao.getOutfit("monday").asLiveData()
    }

}

// view model factory that takes a itemDao as a property and
// creates a WardrobeViewModel
class OutfitViewModelFactory(private val outfitDao: OutfitDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OutfitViewModelWIP::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OutfitViewModelWIP(outfitDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}