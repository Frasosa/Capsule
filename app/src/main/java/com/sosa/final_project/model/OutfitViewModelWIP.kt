package com.sosa.final_project.model

import androidx.lifecycle.*
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.Outfit
import com.sosa.final_project.data.OutfitDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class OutfitViewModelWIP(private val outfitDao: OutfitDao) : ViewModel() {

    val monday: LiveData<Outfit> = outfitDao.getOutfit("monday").asLiveData()
    val tuesday: LiveData<Outfit> = outfitDao.getOutfit("tuesday").asLiveData()
    val wednesday: LiveData<Outfit> = outfitDao.getOutfit("wednesday").asLiveData()
    val thursday: LiveData<Outfit> = outfitDao.getOutfit("thursday").asLiveData()
    val friday: LiveData<Outfit> = outfitDao.getOutfit("friday").asLiveData()
    val saturday: LiveData<Outfit> = outfitDao.getOutfit("saturday").asLiveData()
    val sunday: LiveData<Outfit> = outfitDao.getOutfit("sunday").asLiveData()

    var currentDay: LiveData<Outfit> = monday


    //inserts an item into the database
    fun addOutfit(outfit: Outfit) = viewModelScope.launch(Dispatchers.IO) {
        outfitDao.insertOutfit(outfit)
    }

    //deletes an item from the database
    fun deleteOutfit(outfit: Outfit) {
        viewModelScope.launch(Dispatchers.IO) {
            outfitDao.deleteOutfit(outfit)
        }
    }

    //gets an item given an id from the database
    fun updateOutfit(outfit: Outfit) {
        viewModelScope.launch(Dispatchers.IO) {
            outfitDao.updateOutfit(outfit)
        }
    }

    fun setOutfit(day: String) {
        if (day == "monday")
            currentDay = monday
        else if (day == "tuesday")
                currentDay = tuesday
    }

}

// view model factory that takes a itemDao as a property and
// creates a WardrobeViewModel
class OutfitViewModelFactory(private val outfitDao: OutfitDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OutfitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OutfitViewModelWIP(outfitDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}