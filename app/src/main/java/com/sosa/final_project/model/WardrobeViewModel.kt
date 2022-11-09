package com.sosa.final_project.model

import android.widget.Toast
import androidx.lifecycle.*
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WardrobeViewModel(private val itemDao: ItemDao) : ViewModel() {

    //list of all item in the wardrobe
    val wardrobe: LiveData<List<Item>> = itemDao.getAllItems().asLiveData()


    //inserts an item into the database
    fun addItem(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        itemDao.insertItem(item)
    }

    //deletes an item from the database
    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.deleteItem(item)
        }
    }

    //gets an item given an id from the database
    fun getItem (id : Int) : LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
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