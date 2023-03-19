package com.example.supportapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.DonationsAdapter
import com.example.supportapp.Adapters.myDonationsAdapter
import com.example.supportapp.DataClasses.DonationsData
import com.example.supportapp.DataClasses.myDonationsData

class myDonationsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<myDonationsData>()
    private lateinit var adapter: myDonationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_my_donations, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        addDataToList()
        adapter = myDonationsAdapter(mList)
        recyclerView.adapter = adapter



        return view
    }

    private fun addDataToList(){
        mList.add(myDonationsData("My donations...", R.drawable.unselected_requests))
        mList.add(myDonationsData("My donations...", R.drawable.unselected_requests))
        mList.add(myDonationsData("My donations...", R.drawable.unselected_requests))

    }

}