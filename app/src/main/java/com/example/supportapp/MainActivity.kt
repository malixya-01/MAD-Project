package com.example.supportapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavView: BottomNavigationView

    //Initializing fragments
    val fragementRequests = RequestsFragment()
    val fragementDonations = DonationsFragment()
    val fragementFundrasing = FundrasingFragment()
    val fragementDashboard = DashboardFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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





    }

    // method to replace the layout with the requests fragment
    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        return true
    }


}