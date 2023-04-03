package com.example.supportapp.Fragments.Requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.Adapters.myRequestsAdapter
import com.example.supportapp.R
import com.google.android.material.tabs.TabLayout

class myRequestsFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_requsts, container, false)

        tabLayout = view.findViewById(R.id.tabLaoyut)
        viewPager = view.findViewById(R.id.viewPager)

        var pubReqs = getString(R.string.pubReqs)
        var sentReqs = getString(R.string.sentReqs)

        tabLayout.addTab(tabLayout.newTab().setText(pubReqs))
        tabLayout.addTab(tabLayout.newTab().setText(sentReqs))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = myRequestsAdapter(this, childFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })




        return view
    }
}