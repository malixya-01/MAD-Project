package com.example.supportapp.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.supportapp.Fragments.Requests.MyRequestsTabs.myPublishedReqsFragment
import com.example.supportapp.Fragments.Requests.MyRequestsTabs.mySentReqsFragment
import com.example.supportapp.Fragments.Requests.PublishedRequests.ViewAllDonorsFragment
import com.example.supportapp.Fragments.Requests.myRequestsFragment


internal class myRequestsAdapter(var context: myRequestsFragment, fm: FragmentManager, var totalTabs: Int) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                myPublishedReqsFragment()
            }

            1 -> {
                ViewAllDonorsFragment()
            }

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}