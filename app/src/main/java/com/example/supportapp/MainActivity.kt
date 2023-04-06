package com.example.supportapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    //Bottom nav bar variable
    lateinit var bottomNavView: BottomNavigationView
    //navigation controller variable
    private lateinit var navController: NavController

    //variable to remove back icons from top level destinations
    lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting up navHost fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragsContainer) as NavHostFragment
        navController=navHostFragment.navController

        //removing back button from main destinations
        appBarConfiguration = AppBarConfiguration(
            //passing top level destinations
            setOf(
                R.id.requestsFragment,
                R.id.donationsFragment,
                R.id.fundrasingFragment,
                R.id.dashboardFragment,
                R.id.savedItemesFragment,
                R.id.notificationsFragment
            )
        )

        //setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavView = findViewById(R.id.bottom_navigation)
        bottomNavView.setupWithNavController(navController)

    }

    //adding toolbar  menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    //toggle toolbar icon click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.notify -> {
                // Navigate to notifications frag
                intent = Intent(applicationContext, NotificationsActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.Saved -> {
                // Navigate to saved itemes frag
                intent = Intent(applicationContext, SavedItemesActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    //to implement correct backward navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



}