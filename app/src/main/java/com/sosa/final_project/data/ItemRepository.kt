package com.sosa.final_project.data

import androidx.lifecycle.LiveData

class ItemRepository(private val itemDao: ItemDao) {

    val readAllData: LiveData<List<Item>> = itemDao.getAllItems()

    suspend fun addItem(item: Item) {
        itemDao.insertItem(item)
    }
}