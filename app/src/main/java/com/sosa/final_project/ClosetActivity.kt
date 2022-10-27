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
        val recyclerView = findViewById<RecyclerView>(R.id.poke_recycler_view)
        recyclerView.adapter = PokeCardAdapter(this, intent.getStringExtra("sortID"))

        // Increases performance
        recyclerView.setHasFixedSize(true)

        // Enable up button for backward navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}