package com.sosa.final_project

import android.app.Fragment
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.sosa.final_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding object instance corresponding to the activity_main.xml layout
    // when the view hierarchy is attached to the fragment.
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // retrieve NavController from the NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // set up bottom navigation
        setupActionBarWithNavController(navController,
            AppBarConfiguration(setOf(R.id.wardrobeFragment, R.id.homeFragment, R.id.weatherFragment)))
        binding.bottomNavigationMenu.setupWithNavController(navController)


        // override home click to go back to home fragment, without impacting bottom nav bar setup
        binding.bottomNavigationMenu.setOnNavigationItemSelectedListener {
            if (NavigationUI.onNavDestinationSelected(it, navController)) {
                true
            } else {
                while (navController.currentDestination != navController.findDestination(R.id.homeFragment))
                    navController.navigateUp()
                true
            }
        }
    }

    // override back button functionality for action bar
    override fun onSupportNavigateUp(): Boolean {
        // navigate all the way back to home screen
        while (navController.currentDestination != navController.findDestination(R.id.homeFragment))
            navController.navigateUp()
        return true
    }


}