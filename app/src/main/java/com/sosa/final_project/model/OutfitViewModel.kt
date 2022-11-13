package com.sosa.final_project.model

import androidx.lifecycle.*
import com.sosa.final_project.data.converters.OutfitConverter
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.Outfit
import com.sosa.final_project.data.OutfitDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OutfitViewModel(private val outfitDao: OutfitDao) : ViewModel() {

    // data persistent outfits for each day
    private val sundayOutfit: LiveData<Outfit> = outfitDao.getOutfit("sunday")
    private val mondayOutfit: LiveData<Outfit> = outfitDao.getOutfit("monday")
    private val tuesdayOutfit: LiveData<Outfit> = outfitDao.getOutfit("tuesday")
    private val wednesdayOutfit: LiveData<Outfit> = outfitDao.getOutfit("wednesday")
    private val thursdayOutfit: LiveData<Outfit> = outfitDao.getOutfit("thursday")
    private val fridayOutfit: LiveData<Outfit> = outfitDao.getOutfit("friday")
    private val saturdayOutfit: LiveData<Outfit> = outfitDao.getOutfit("saturday")

    // variables to help display and edit the current outfit based on the day clicked
    lateinit var currentOutfit: LiveData<Outfit>
    lateinit var currentDay: String

    // keeps track of items (bitmaps as strings) selected
    private var selectedItems = mutableListOf<String>()

    fun selectItem(item: Item) {
        selectedItems.add(OutfitConverter.BitMapToString(item.image))
    }

    fun deselectItem(item: Item) {
        selectedItems.remove(OutfitConverter.BitMapToString(item.image))
    }

    fun updateOutfit() {
        // copy over selected list
        val tmpList = mutableListOf<String>()
        tmpList.addAll(selectedItems)

        if (currentOutfit.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                currentOutfit.value?.items?.addAll(tmpList)
                outfitDao.updateOutfit(currentOutfit.value!!)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                outfitDao.insertOutfit(Outfit(currentDay, tmpList))
            }
        }
        // clear selected list for next add
        selectedItems.clear()
    }

    // will either update the outfit by adding the item or initialize it in the database
    fun updateOutfitInsertion(item: Item) {
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

    // will either update the outfit to remove and item
    fun updateOutfitRemoval(item: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currentOutfit.value?.items?.remove(item)
            outfitDao.updateOutfit(currentOutfit.value!!)
        }
}

    // deletes an item from the database
    fun deleteOutfit() {
        if (currentOutfit.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                outfitDao.updateOutfit(Outfit(currentDay, mutableListOf()))
            }
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