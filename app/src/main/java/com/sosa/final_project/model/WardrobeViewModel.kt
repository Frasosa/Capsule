package com.sosa.final_project.model

import androidx.lifecycle.*
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.ItemDao
import com.sosa.final_project.data.converters.OutfitConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WardrobeViewModel(private val itemDao: ItemDao) : ViewModel() {

    // list of all item in the wardrobe
    val wardrobe: LiveData<List<Item>> = itemDao.getWardrobe()

    // list of all items currently selected
    private val selectedItems = mutableListOf<Item>()

    // functions for interacting with the selected list
    fun selectItem(item: Item) {
        selectedItems.add(item)
    }

    fun deselectItem(item: Item) {
        selectedItems.remove(item)
    }

    // deletes all the items selected
    fun trashItems() {
        selectedItems.forEach {
            removeItem(it)
        }
    }

    // inserts an item into the database
    fun addItem(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        itemDao.insertItem(item)
    }

    // removes an item from the database
    fun removeItem (item: Item) = viewModelScope.launch(Dispatchers.IO) {
        itemDao.deleteItem(item)
    }

    // returns true if the wardrobe is empty
    fun isEmpty() : Boolean {
        return if (wardrobe.value == null)
            true
        else wardrobe.value!!.isEmpty()
    }

}

// view model factory that takes a itemDao as a property and
// creates a WardrobeViewModel
class WardrobeViewModelFactory(private val itemDao: ItemDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WardrobeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WardrobeViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}