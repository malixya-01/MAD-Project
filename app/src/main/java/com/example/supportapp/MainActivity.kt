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
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    //Bottom nav bar variable
    lateinit var bottomNavView: BottomNavigationView
    //navigation controller variable
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creating reequests fragment as HostNav(home page)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentRequest) as NavHostFragment
        navController=navHostFragment.navController

        //setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)

        bottomNavView = findViewById(R.id.bottom_navigation)
        bottomNavView.setupWithNavController(navController)




    }
}