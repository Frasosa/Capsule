package com.sosa.final_project.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sosa.final_project.data.DataSource

class OutfitViewModel : ViewModel() {
    //Complete list of outfits for the week
    val weeklyOutfits = DataSource.weeklyOutfits

    //TODO: ADD MUTABLE AND LIVE DATA

    //Current outfit for outfit fragment to display
    private val _outfit = MutableLiveData<MutableList<Int>>()
    val outfit: LiveData<MutableList<Int>> = _outfit


    /* function to set the outfit for the outfit fragment depending on the day */
    fun setOutfit (day: String) {
        _outfit.value = weeklyOutfits[day]
    }

    /* function to set the outfit for the outfit fragment depending on the day */
    fun getOutfit (): MutableList<Int> {
        return outfit.value!!
    }

    fun entireWardrobe () {
        _outfit.value = DataSource.wardrobe
    }

    //TODO: ADD FUNCTION TO EDIT AN OUTFIT FOR A DAY

    //TODO: ADD FUNCTION TO CLEAR AN OUTFIT FOR A DAY

    //TODO: ADD FUNCTION TO CLEAR FUNCTION FOR THE WEEK?
}