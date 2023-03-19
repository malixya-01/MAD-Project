package com.example.supportapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DonationsFragment : Fragment(R.layout.fragment_donations) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<DonationsData>()
    private lateinit var adapter: DonationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_donations, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        addDataToList()
        adapter = DonationsAdapter(mList)
        recyclerView.adapter = adapter

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_donationsFragment_to_newDonationFragment)
        }

        return view
    }

    private fun addDataToList(){
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))
        mList.add(DonationsData("I can donate ...", R.drawable.unselected_donorads))


    }

}