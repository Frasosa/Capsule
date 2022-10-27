package com.sosa.final_project

import android.os.Bundle
import android.os.strictmode.InstanceCountViolation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ClosetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closet)

        // Initialize recyclerview
        val recyclerView = findViewById<RecyclerView>(R.id.closet_recycler_view)
        // Increases performance
        recyclerView.setHasFixedSize(true)
    }
}