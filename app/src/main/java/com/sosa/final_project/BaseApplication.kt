package com.sosa.final_project

import android.app.Application
import com.sosa.final_project.data.ItemDatabase

class BaseApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: ItemDatabase by lazy {
        ItemDatabase.getDatabase(this)
    }
}