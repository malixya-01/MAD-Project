package com.example.supportapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
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

        //Creating reequests fragment as HostNav(home page)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentRequest) as NavHostFragment
        navController=navHostFragment.navController


        //removing back button from main destinations
        appBarConfiguration = AppBarConfiguration(
            //passing top level destinations
            setOf(
                R.id.requestsFragment,
                R.id.donationsFragment,
                R.id.fundrasingFragment,
                R.id.dashboardFragment
            )
        )

        //setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavView = findViewById(R.id.bottom_navigation)
        bottomNavView.setupWithNavController(navController)


    }

    //to implement correct backward navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}