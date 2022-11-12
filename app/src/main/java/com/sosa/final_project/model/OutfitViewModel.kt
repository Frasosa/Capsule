package com.sosa.final_project.model

import androidx.lifecycle.*
import com.sosa.final_project.data.converters.OutfitConverter
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.Outfit
import com.sosa.final_project.data.OutfitDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OutfitViewModel(private val outfitDao: OutfitDao) : ViewModel() {

    private var sundayOutfit: LiveData<Outfit> = outfitDao.getOutfit("sunday")
    private var mondayOutfit: LiveData<Outfit> = outfitDao.getOutfit("monday")
    private var tuesdayOutfit: LiveData<Outfit> = outfitDao.getOutfit("tuesday")
    private var wednesdayOutfit: LiveData<Outfit> = outfitDao.getOutfit("wednesday")
    private var thursdayOutfit: LiveData<Outfit> = outfitDao.getOutfit("thursday")
    private var fridayOutfit: LiveData<Outfit> = outfitDao.getOutfit("friday")
    private var saturdayOutfit: LiveData<Outfit> = outfitDao.getOutfit("saturday")

    lateinit var currentOutfit: LiveData<Outfit>
    lateinit var currentDay: String


    // will either update the outfit for that day or initialize it in the database
    fun updateOutfit(item: Item) {
        if (currentOutfit.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                currentOutfit.value?.items?.add(OutfitConverter.BitMapToString(item.image))
                outfitDao.updateOutfit(currentOutfit.value!!)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val tmpList = mutableListOf<String>()
                tmpList.add(OutfitConverter.BitMapToString(item.image))
                outfitDao.insertOutfit(Outfit(currentDay, tmpList))
            }
        }
    }

    // deletes an item from the database
    fun deleteOutfit(outfit: Outfit) {
        viewModelScope.launch(Dispatchers.IO) {
            outfitDao.deleteOutfit(outfit)
        }
    }

    // sets the current outfit to the day chosen
    fun setOutfit(day: String) {
        currentOutfit = when (day) {
            "sunday" -> sundayOutfit
            "monday" -> mondayOutfit
            "tuesday" -> tuesdayOutfit
            "wednesday" -> wednesdayOutfit
            "thursday" -> thursdayOutfit
            "friday" -> fridayOutfit
            else -> saturdayOutfit
        }
        currentDay = day
    }
}

// view model factory that takes a OutfitDao as a property and
// creates a OutfitViewModel
class OutfitViewModelFactory(private val outfitDao: OutfitDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OutfitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OutfitViewModel(outfitDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}