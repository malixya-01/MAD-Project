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

    /*
    //Bottom nav bar variables
    lateinit var bottomNavView: BottomNavigationView
    val fragementRequests = RequestsFragment()
    val fragementDonations = DonationsFragment()
    val fragementFundrasing = FundrasingFragment()
    val fragementDashboard = DashboardFragment()
     */

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creating reequests fragment as HostNav(home page)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentRequest) as NavHostFragment
        navController=navHostFragment.navController

        //setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)


        /*
        bottomNavView = findViewById(R.id.bottom_navigation)

        // display the requests fragment as home page
        replaceFragment(fragementRequests)

        //Connecting fragments to the nav bar
        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.requests -> replaceFragment(fragementRequests)
                R.id.donorsads -> replaceFragment(fragementDonations)
                R.id.fundraising -> replaceFragment(fragementFundrasing)
                R.id.dashboard -> replaceFragment(fragementDashboard)
                else -> true
            }
        }

         */



    }

    //to implement correct backward navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



    // method to replace the layout with the requests fragment
    /*private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        return true
    }

     */


}