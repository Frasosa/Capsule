package com.sosa.final_project.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.ItemDatabase
import com.sosa.final_project.data.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WardrobeViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<Item>>
    private val repository: ItemRepository

    init {
        val itemDao = ItemDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        readAllData = repository.readAllData
    }

    //
    fun addItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }

}