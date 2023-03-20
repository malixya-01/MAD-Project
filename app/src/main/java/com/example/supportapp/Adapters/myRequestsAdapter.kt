package com.example.supportapp.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.supportapp.Fragments.myPublishedReqsFragment
import com.example.supportapp.Fragments.myRequestsFragment
import com.example.supportapp.Fragments.mySentReqsFragment

internal class myRequestsAdapter(var context: myRequestsFragment, fm: FragmentManager, var totalTabs: Int) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                myPublishedReqsFragment()
            }

            1 -> {
                mySentReqsFragment()
            }

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}