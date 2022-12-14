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

    // functions for interacting with the selected list
    fun selectItem(item: Item) {
        selectedItems.add(OutfitConverter.BitMapToString(item.image))
    }

    fun deselectItem(item: Item) {
        selectedItems.remove(OutfitConverter.BitMapToString(item.image))
    }

    fun deselectAll() {
        selectedItems.clear()
    }

    fun selectItem(item: String) {
        selectedItems.add(item)
    }

    fun deselectItem(item: String) {
        selectedItems.remove(item)
    }

    fun removeItems() {
        // copy over selected list
        val tmpList = mutableListOf<String>()
        tmpList.addAll(selectedItems)

        // if the outfit is not already empty, remove selected items and update it
        if (currentOutfit.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                currentOutfit.value?.items?.removeAll(tmpList)
                outfitDao.updateOutfit(currentOutfit.value!!)
            }
            deselectAll()
        }
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